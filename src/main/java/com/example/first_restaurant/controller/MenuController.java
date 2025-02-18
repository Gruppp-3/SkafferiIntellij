package com.example.first_restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.first_restaurant.service.DishService;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class MenuController {

    @Autowired
    private DishService dishService;

    @GetMapping("/")
    public String showMenu(Model model) {
        // Get à la carte menu
        List<Map<String, Object>> menuItems = dishService.getMenuByCategory();
        Map<String, List<Map<String, Object>>> categorizedMenu = new HashMap<>();

        for (Map<String, Object> item : menuItems) {
            String category = (String) item.get("DISH_TYPE_NAME");
            categorizedMenu.computeIfAbsent(category, k -> new ArrayList<>()).add(item);
        }
        model.addAttribute("categorizedMenu", categorizedMenu);

        // Get today's lunch menu
        List<Map<String, Object>> todayLunch = dishService.getTodayLunch();
        model.addAttribute("todayLunch", todayLunch);

        // Get weekly lunch menu
        Map<String, List<Map<String, Object>>> weeklyLunchMenu = dishService.getWeeklyLunchMenu();
        model.addAttribute("weeklyLunchMenu", weeklyLunchMenu);

        return "index";
    }
}