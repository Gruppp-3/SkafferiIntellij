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

    @Autowired
    private LunchMenuRepository lunchMenuRepository;

    @Autowired
    private LunchDishRepository lunchDishRepository;

    // Returns today's lunch dishes as a list of maps.
    public List<Map<String, Object>> getTodayLunch() {
        Optional<LunchMenu> menuOpt = lunchMenuRepository.findByLunchDate(LocalDate.now());
        if (menuOpt.isPresent()) {
            return menuOpt.get().getLunchDishes().stream()
                    .map(this::convertToMap)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    // Returns the upcoming week's menu starting from next Monday.
    public Map<String, List<Map<String, Object>>> getUpcomingWeekMenu() {
        LocalDate nextMonday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDate endOfWeek = nextMonday.plusDays(6);
        List<LunchMenu> weeklyMenus = lunchMenuRepository.findByLunchDateBetween(nextMonday, endOfWeek);
        Map<String, List<Map<String, Object>>> weeklyLunch = new HashMap<>();
        String[] days = {"Måndag", "Tisdag", "Onsdag", "Torsdag", "Fredag", "Lördag", "Söndag"};
        for (LunchMenu menu : weeklyMenus) {
            String dayName = days[menu.getLunchDate().getDayOfWeek().getValue() - 1];
            List<Map<String, Object>> dishes = menu.getLunchDishes().stream()
                    .map(this::convertToMap)
                    .collect(Collectors.toList());
            weeklyLunch.put(dayName, dishes);
        }
        return weeklyLunch;
    }

    // Adds a new lunch dish. The client must send a JSON payload with:
    // "dish_name", "dish_description", "dish_price", and "date" (ISO format)
    public Map<String, Object> addLunchDish(Map<String, Object> lunchDishData) {
        LocalDate date = lunchDishData.containsKey("date") ?
                LocalDate.parse((String) lunchDishData.get("date")) : LocalDate.now();

        // Find or create the LunchMenu for the given date.
        LunchMenu menu = lunchMenuRepository.findByLunchDate(date)
                .orElseGet(() -> {
                    LunchMenu newMenu = new LunchMenu();
                    newMenu.setLunchDate(date);
                    return lunchMenuRepository.save(newMenu);
                });

        LunchDish dish = new LunchDish();
        dish.setName((String) lunchDishData.get("dish_name"));
        dish.setDescription((String) lunchDishData.get("dish_description"));
        dish.setPrice(new BigDecimal(lunchDishData.get("dish_price").toString()));
        dish.setLunchMenu(menu);

        LunchDish savedDish = lunchDishRepository.save(dish);
        return convertToMap(savedDish);
    }

    // Updates an existing lunch dish. Expects a map with keys "dish_name", "dish_description", and "dish_price".
    public Map<String, Object> updateLunchDish(Long id, Map<String, Object> lunchDishData) {
        log.debug("Attempting to update lunch dish with ID: {} and data: {}", id, lunchDishData);
        LunchDish dish = lunchDishRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lunch dish not found: " + id));

        dish.setName((String) lunchDishData.get("dish_name"));
        dish.setDescription((String) lunchDishData.get("dish_description"));
        dish.setPrice(new BigDecimal(lunchDishData.get("dish_price").toString()));
        LunchDish updatedDish = lunchDishRepository.save(dish);
        return convertToMap(updatedDish);
    }

    // Deletes a lunch dish by its ID.
    public void deleteLunchDish(Long id) {
        lunchDishRepository.deleteById(id);
    }

    // Creates (or resets) a weekly menu. The weeklyMenu parameter is a map
    // where the key is a Swedish day name and the value is a list of dish maps.
    // The startOfWeek parameter is the ISO date string for the Monday of that week.
    public void createWeeklyMenu(Map<String, List<Map<String, Object>>> weeklyMenu, LocalDate startOfWeek) {
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        List<LunchMenu> existingMenus = lunchMenuRepository.findByLunchDateBetween(startOfWeek, endOfWeek);
        lunchMenuRepository.deleteAll(existingMenus);

        for (Map.Entry<String, List<Map<String, Object>>> entry : weeklyMenu.entrySet()) {
            String day = entry.getKey();
            List<Map<String, Object>> dishes = entry.getValue();

            LunchMenu menu = new LunchMenu();
            menu.setLunchDate(startOfWeek.plusDays(getDayOffset(day)));
            menu = lunchMenuRepository.save(menu);

            // For weekly menus, we assume each dish map has keys "name", "description", and "price".
            for (Map<String, Object> dishData : dishes) {
                LunchDish dish = new LunchDish();
                dish.setName((String) dishData.get("name"));
                dish.setDescription((String) dishData.get("description"));
                dish.setPrice((BigDecimal) dishData.get("price"));
                dish.setLunchMenu(menu);
                lunchDishRepository.save(dish);
            }
        }
    }

    // Validates whether the given date falls within the upcoming week (next Monday to next Sunday).
    public boolean validateUpcomingDate(LocalDate requestedDate) {
        LocalDate nextMonday = LocalDate.now();
        while (nextMonday.getDayOfWeek() != DayOfWeek.MONDAY) {
            nextMonday = nextMonday.plusDays(1);
        }
        nextMonday = nextMonday.plusWeeks(1);
        LocalDate nextSunday = nextMonday.plusDays(6);
        return !requestedDate.isBefore(nextMonday) && !requestedDate.isAfter(nextSunday);
    }

    // Converts a LunchDish entity to a map representation.
    private Map<String, Object> convertToMap(LunchDish dish) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", dish.getId());
        map.put("dish_name", dish.getName());
        map.put("dish_description", dish.getDescription());
        map.put("dish_price", dish.getPrice());
        map.put("date", dish.getLunchMenu().getLunchDate().toString());
        return map;
    }

    // Maps Swedish day names to an offset (0 for Måndag, 1 for Tisdag, …, 6 for Söndag).
    private int getDayOffset(String day) {
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

    public Map<String, List<Map<String, Object>>> getWeeklyLunch() {
        LocalDate startOfWeek = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        List<LunchMenu> weeklyMenus = lunchMenuRepository.findByLunchDateBetween(startOfWeek, endOfWeek);
        Map<String, List<Map<String, Object>>> weeklyLunch = new HashMap<>();
        String[] days = {"Måndag", "Tisdag", "Onsdag", "Torsdag", "Fredag", "Lördag", "Söndag"};

        weeklyMenus.forEach(menu -> {
            String dayName = days[menu.getLunchDate().getDayOfWeek().getValue() - 1];
            List<Map<String, Object>> dishes = menu.getLunchDishes().stream()
                    .map(this::convertToMap)
                    .collect(Collectors.toList());
            weeklyLunch.put(dayName, dishes);
        });

        return weeklyLunch;
    }

}
