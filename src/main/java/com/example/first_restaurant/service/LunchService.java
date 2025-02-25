package com.example.first_restaurant.service;

import com.example.first_restaurant.entity.LunchDish;
import com.example.first_restaurant.entity.LunchMenu;
import com.example.first_restaurant.repository.LunchDishRepository;
import com.example.first_restaurant.repository.LunchMenuRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LunchService {
    private static final String[] SWEDISH_DAYS = {
            "Måndag", "Tisdag", "Onsdag", "Torsdag", "Fredag", "Lördag", "Söndag"
    };

    @Autowired
    private LunchMenuRepository lunchMenuRepository;

    @Autowired
    private LunchDishRepository lunchDishRepository;

    // Returns today's lunch dishes
    public List<Map<String, Object>> getTodayLunch() {
        return getLunchForDate(LocalDate.now());
    }

    // Returns the weekly lunch menu starting from current week's Monday
    public Map<String, List<Map<String, Object>>> getWeeklyLunch() {
        LocalDate startOfWeek = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        return getLunchForDateRange(startOfWeek, endOfWeek);
    }

    // Returns the upcoming week's menu starting from next Monday
    public Map<String, List<Map<String, Object>>> getUpcomingWeekMenu() {
        LocalDate nextMonday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDate endOfWeek = nextMonday.plusDays(6);
        return getLunchForDateRange(nextMonday, endOfWeek);
    }

    // Adds a new lunch dish
    public Map<String, Object> addLunchDish(Map<String, Object> lunchDishData) {
        LocalDate date = lunchDishData.containsKey("date") ?
                LocalDate.parse((String) lunchDishData.get("date")) : LocalDate.now();

        // Find or create the LunchMenu for the given date
        LunchMenu menu = findOrCreateMenuForDate(date);

        LunchDish dish = new LunchDish();
        dish.setName((String) lunchDishData.get("dish_name"));
        dish.setDescription((String) lunchDishData.get("dish_description"));
        dish.setPrice(new BigDecimal(lunchDishData.get("dish_price").toString()));
        dish.setLunchMenu(menu);

        LunchDish savedDish = lunchDishRepository.save(dish);
        return convertToMap(savedDish);
    }

    // Updates an existing lunch dish
    public Map<String, Object> updateLunchDish(Long id, Map<String, Object> lunchDishData) {
        log.debug("Updating lunch dish with ID: {} and data: {}", id, lunchDishData);

        LunchDish dish = lunchDishRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lunch dish not found: " + id));

        dish.setName((String) lunchDishData.get("dish_name"));
        dish.setDescription((String) lunchDishData.get("dish_description"));
        dish.setPrice(new BigDecimal(lunchDishData.get("dish_price").toString()));

        LunchDish updatedDish = lunchDishRepository.save(dish);
        return convertToMap(updatedDish);
    }

    // Deletes a lunch dish by its ID
    public void deleteLunchDish(Long id) {
        lunchDishRepository.deleteById(id);
    }

    // Creates (or resets) a weekly menu
    public void createWeeklyMenu(Map<String, List<Map<String, Object>>> weeklyMenu, LocalDate startOfWeek) {
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        // Clear existing menu for the week
        List<LunchMenu> existingMenus = lunchMenuRepository.findByLunchDateBetween(startOfWeek, endOfWeek);
        lunchMenuRepository.deleteAll(existingMenus);

        // Create new menu for each day
        for (Map.Entry<String, List<Map<String, Object>>> entry : weeklyMenu.entrySet()) {
            String day = entry.getKey();
            List<Map<String, Object>> dishes = entry.getValue();

            // Create a menu for the day
            LunchMenu menu = new LunchMenu();
            menu.setLunchDate(startOfWeek.plusDays(getDayOffset(day)));
            menu = lunchMenuRepository.save(menu);

            // Add dishes to the menu
            for (Map<String, Object> dishData : dishes) {
                LunchDish dish = new LunchDish();
                dish.setName((String) dishData.get("name"));
                dish.setDescription((String) dishData.get("description"));
                dish.setPrice(new BigDecimal(dishData.get("price").toString()));
                dish.setLunchMenu(menu);
                lunchDishRepository.save(dish);
            }
        }
    }

    // Validates whether the given date falls within the upcoming week
    public boolean validateUpcomingDate(LocalDate requestedDate) {
        LocalDate nextMonday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDate nextSunday = nextMonday.plusDays(6);
        return !requestedDate.isBefore(nextMonday) && !requestedDate.isAfter(nextSunday);
    }

    // Helper methods
    private List<Map<String, Object>> getLunchForDate(LocalDate date) {
        Optional<LunchMenu> menuOpt = lunchMenuRepository.findByLunchDate(date);
        if (menuOpt.isPresent()) {
            return menuOpt.get().getLunchDishes().stream()
                    .map(this::convertToMap)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private Map<String, List<Map<String, Object>>> getLunchForDateRange(LocalDate startDate, LocalDate endDate) {
        List<LunchMenu> menus = lunchMenuRepository.findByLunchDateBetween(startDate, endDate);
        Map<String, List<Map<String, Object>>> result = new HashMap<>();

        for (LunchMenu menu : menus) {
            String dayName = SWEDISH_DAYS[menu.getLunchDate().getDayOfWeek().getValue() - 1];
            List<Map<String, Object>> dishes = menu.getLunchDishes().stream()
                    .map(this::convertToMap)
                    .collect(Collectors.toList());
            result.put(dayName, dishes);
        }

        return result;
    }

    private LunchMenu findOrCreateMenuForDate(LocalDate date) {
        return lunchMenuRepository.findByLunchDate(date)
                .orElseGet(() -> {
                    LunchMenu newMenu = new LunchMenu();
                    newMenu.setLunchDate(date);
                    return lunchMenuRepository.save(newMenu);
                });
    }

    private Map<String, Object> convertToMap(LunchDish dish) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", dish.getId());
        map.put("dish_name", dish.getName());
        map.put("dish_description", dish.getDescription());
        map.put("dish_price", dish.getPrice());
        map.put("date", dish.getLunchMenu().getLunchDate().toString());
        return map;
    }

    private int getDayOffset(String day) {
        for (int i = 0; i < SWEDISH_DAYS.length; i++) {
            if (SWEDISH_DAYS[i].equals(day)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Invalid day: " + day);
    }
}