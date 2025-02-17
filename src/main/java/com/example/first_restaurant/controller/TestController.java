package com.example.first_restaurant.controller;
import com.example.first_restaurant.entity.Dish;
import com.example.first_restaurant.service.DishService;
import com.example.first_restaurant.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// Initial Test Spring Boot

@RestController
@RequestMapping("/api/menu")
public class TestController {
    @Autowired
    private DishService dishService;

    @GetMapping
    public List<Map<String, Object>> getMenu() {
        return dishService.getMenuByCategory();
    }

    @GetMapping("/print")
    public String printMenu() {
        List<Map<String, Object>> menu = dishService.getMenuByCategory();
        StringBuilder formattedMenu = new StringBuilder();
        String currentCategory = "";

        for (Map<String, Object> item : menu) {
            String category = (String) item.get("DISH_TYPE_NAME");
            if (!category.equals(currentCategory)) {
                formattedMenu.append("\n=== ").append(category).append(" ===\n");
                currentCategory = category;
            }

            formattedMenu.append(String.format("%-30s %5.2f kr\n",
                    item.get("DISH_NAME"),
                    ((Number) item.get("DISH_PRICE")).doubleValue()));
            formattedMenu.append("    ").append(item.get("DISH_DESCRIPTION")).append("\n\n");
        }

        return formattedMenu.toString();
    }

}
