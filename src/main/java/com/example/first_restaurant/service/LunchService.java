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
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LunchService {
    @Autowired
    private LunchMenuRepository lunchMenuRepository;

    @Autowired
    private LunchDishRepository lunchDishRepository;

    public List<Map<String, Object>> getTodayLunch() {
        Optional<LunchMenu> menu = lunchMenuRepository.findByLunchDate(LocalDate.now());
        if (menu.isPresent()) {
            List<Map<String, Object>> dishes = menu.get().getLunchDishes().stream()
                    .map(dish -> {
                        Map<String, Object> map = convertToMap(dish);
                        System.out.println("Dish data: " + map);  // Add this line
                        return map;
                    })
                    .collect(Collectors.toList());
            return dishes;
        }
        return new ArrayList<>();
    }

    // Changed method name to match controller and return type
    public Map<String, List<Map<String, Object>>> getWeeklyLunch() {  // was getWeeklyLunchMenu
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

    // Changed return type to Map<String, Object>
    public Map<String, Object> addLunchDish(Map<String, Object> lunchDish) {
        LocalDate date = lunchDish.containsKey("date") ?
                LocalDate.parse((String) lunchDish.get("date")) :
                LocalDate.now();

        LunchMenu menu = lunchMenuRepository.findByLunchDate(date)
                .orElseGet(() -> {
                    LunchMenu newMenu = new LunchMenu();
                    newMenu.setLunchDate(date);
                    return lunchMenuRepository.save(newMenu);
                });

        LunchDish dish = new LunchDish();
        dish.setName((String) lunchDish.get("dish_name"));
        dish.setDescription((String) lunchDish.get("dish_description"));
        dish.setPrice(new BigDecimal(lunchDish.get("dish_price").toString()));
        dish.setLunchMenu(menu);

        LunchDish savedDish = lunchDishRepository.save(dish);
        return convertToMap(savedDish);  // Make sure this returns Map<String, Object>
    }

    // Changed return type to Map<String, Object>
    public Map<String, Object> updateLunchDish(Long id, Map<String, Object> lunchDish) {
        log.debug("Attempting to update lunch dish with ID: {} and data: {}", id, lunchDish);

        LunchDish dish = lunchDishRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lunch dish not found: " + id));

        log.debug("Found existing dish: {}", dish);

        dish.setName((String) lunchDish.get("dish_name"));
        dish.setDescription((String) lunchDish.get("dish_description"));
        dish.setPrice(new BigDecimal(lunchDish.get("dish_price").toString()));

        log.debug("Updated dish before save: {}", dish);

        LunchDish updatedDish = lunchDishRepository.save(dish);
        log.debug("Dish after save: {}", updatedDish);

        return convertToMap(updatedDish);
    }

    public void deleteLunchDish(Long id) {
        lunchDishRepository.deleteById(id);
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