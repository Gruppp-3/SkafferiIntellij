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

    // In LunchService.java
    public void createWeeklyMenu(Map<String, List<Map<String, Object>>> weeklyMenu, LocalDate startOfWeek) {
        // Optionally, delete existing menus for the target week.
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        List<LunchMenu> existingMenus = lunchMenuRepository.findByLunchDateBetween(startOfWeek, endOfWeek);
        if (!existingMenus.isEmpty()) {
            lunchMenuRepository.deleteAll(existingMenus);
        }

        // Iterate over each day entry in the payload.
        for (Map.Entry<String, List<Map<String, Object>>> entry : weeklyMenu.entrySet()) {
            String dayName = entry.getKey();
            int offset = getDayOffsetForDay(dayName);
            LocalDate menuDate = startOfWeek.plusDays(offset);
            List<Map<String, Object>> dishes = entry.getValue();

            // For each dish, add the computed date to the data and call addLunchDish(…)
            for (Map<String, Object> dishData : dishes) {
                dishData.put("date", menuDate.toString());
                // Reuse your existing addLunchDish method.
                addLunchDish(dishData);
            }
        }
    }

    // Helper method: Map a Swedish day name to an offset from the provided startOfWeek.
    private int getDayOffsetForDay(String day) {
        switch (day) {
            case "Måndag": return 0;
            case "Tisdag": return 1;
            case "Onsdag": return 2;
            case "Torsdag": return 3;
            case "Fredag": return 4;
            case "Lördag": return 5;
            case "Söndag": return 6;
            default: throw new IllegalArgumentException("Invalid day: " + day);
        }
    }


    // Returns the upcoming week's menu (next Monday to Sunday)
// If no upcoming menu exists, prepopulate by shifting the current week's menu by 7 days.
    public Map<String, List<Map<String, Object>>> getUpcomingWeekMenu() {
        LocalDate nextMonday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDate endOfWeek = nextMonday.plusDays(6);

        // Get menus from the database for next week
        Map<String, List<Map<String, Object>>> upcomingMenu = getLunchForDateRange(nextMonday, endOfWeek);

        // Check if the upcoming menu is empty (all days have no dishes)
        boolean isEmpty = true;
        for (List<Map<String, Object>> dishes : upcomingMenu.values()) {
            if (!dishes.isEmpty()) {
                isEmpty = false;
                break;
            }
        }

        if (isEmpty) {
            // If no menu exists for next week, fetch the current week's menu
            Map<String, List<Map<String, Object>>> currentMenu = getWeeklyLunch();
            Map<String, List<Map<String, Object>>> prepopulatedMenu = new HashMap<>();

            // For each day in the current menu, shift the date by 7 days
            for (String day : currentMenu.keySet()) {
                List<Map<String, Object>> dishes = currentMenu.get(day);
                List<Map<String, Object>> newDishes = new ArrayList<>();
                for (Map<String, Object> dish : dishes) {
                    Map<String, Object> newDish = new HashMap<>(dish);
                    // Shift the dish's date forward by 7 days
                    LocalDate currentDate = LocalDate.parse((String) dish.get("date"));
                    LocalDate newDate = currentDate.plusWeeks(1);
                    newDish.put("date", newDate.toString());
                    newDishes.add(newDish);
                }
                prepopulatedMenu.put(day, newDishes);
            }
            return prepopulatedMenu;
        }

        return upcomingMenu;
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
}