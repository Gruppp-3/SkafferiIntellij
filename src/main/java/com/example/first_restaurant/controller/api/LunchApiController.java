package com.example.first_restaurant.controller.api;

import com.example.first_restaurant.service.LunchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/lunch")
public class LunchApiController {

    @Autowired
    private LunchService lunchService;

    // Get today's lunch dishes.
    @GetMapping("/today")
    public ResponseEntity<List<Map<String, Object>>> getTodayLunch() {
        List<Map<String, Object>> todayLunch = lunchService.getTodayLunch();
        return ResponseEntity.ok(todayLunch);
    }

    // Get the current week's menu (current week: Monday to Sunday).
    @GetMapping("/weekly")
    public ResponseEntity<Map<String, List<Map<String, Object>>>> getWeeklyLunchMenu() {
        Map<String, List<Map<String, Object>>> weeklyMenu = lunchService.getWeeklyLunch();
        return ResponseEntity.ok(weeklyMenu);
    }

    // Get the upcoming week's menu (next week's Monday to Sunday).
    @GetMapping("/nextWeekly")
    public ResponseEntity<Map<String, List<Map<String, Object>>>> getNextWeeklyLunchMenu() {
        Map<String, List<Map<String, Object>>> nextWeeklyMenu = lunchService.getUpcomingWeekMenu();
        return ResponseEntity.ok(nextWeeklyMenu);
    }

    // Add a lunch dish.
    @PostMapping("/today")
    public ResponseEntity<Map<String, Object>> addLunchDish(@RequestBody Map<String, Object> request) {
        try {
            Map<String, Object> savedDish = lunchService.addLunchDish(request);
            return ResponseEntity.ok(savedDish);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update an existing lunch dish.
    @PutMapping("/today/{id}")
    public ResponseEntity<Map<String, Object>> updateLunchDish(
            @PathVariable Long id,
            @RequestBody Map<String, Object> request) {
        try {
            Map<String, Object> updatedDish = lunchService.updateLunchDish(id, request);
            return ResponseEntity.ok(updatedDish);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Delete a lunch dish.
    @DeleteMapping("/today/{id}")
    public ResponseEntity<Void> deleteLunchDish(@PathVariable Long id) {
        lunchService.deleteLunchDish(id);
        return ResponseEntity.ok().build();
    }

    // Validate that a given date is within the upcoming week.
    @GetMapping("/validate-date")
    public ResponseEntity<Boolean> validateDateForUpcomingWeek(@RequestParam String date) {
        try {
            LocalDate requestedDate = LocalDate.parse(date);
            boolean isValid = lunchService.validateUpcomingDate(requestedDate);
            return ResponseEntity.ok(isValid);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }
}
