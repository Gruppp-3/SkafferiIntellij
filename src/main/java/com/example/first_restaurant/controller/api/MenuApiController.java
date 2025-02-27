package com.example.first_restaurant.controller.api;

import com.example.first_restaurant.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/menu")
public class MenuApiController {

    @Autowired
    private DishService dishService;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getMenu() {
        List<Map<String, Object>> menuItems = dishService.getMenuByCategory();
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/today")
    public ResponseEntity<List<Map<String, Object>>> getTodayLunch() {
        List<Map<String, Object>> todayLunch = dishService.getTodayLunch();
        return ResponseEntity.ok(todayLunch);
    }

    @GetMapping("/weekly")
    public ResponseEntity<Map<String, List<Map<String, Object>>>> getWeeklyLunch() {
        Map<String, List<Map<String, Object>>> weeklyMenu = dishService.getWeeklyLunchMenu();
        return ResponseEntity.ok(weeklyMenu);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addMenuItem(@RequestBody Map<String, Object> menuItem) {
        try {
            Map<String, Object> savedItem = dishService.addMenuItem(menuItem);
            return ResponseEntity.ok(savedItem);
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateMenuItem(
            @PathVariable Long id,
            @RequestBody Map<String, Object> menuItem) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        try {
            boolean deleted = dishService.deleteMenuItem(id);
            if (deleted) {
                return ResponseEntity.ok().build(); // 200 OK
            } else {
                return ResponseEntity.notFound().build(); // 404 Not Found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build(); // 500 Internal Server Error
        }
    }
}