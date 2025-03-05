package com.example.first_restaurant.controller.api;

import com.example.first_restaurant.entity.LunchDish;
import com.example.first_restaurant.service.LunchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/lunch")
public class LunchApiController {

    @Autowired
    private LunchService lunchService;

    @GetMapping("/today")
    public ResponseEntity<List<Map<String, Object>>> getTodayLunch() {
        List<Map<String, Object>> todayLunch = lunchService.getTodayLunch();
        return ResponseEntity.ok(todayLunch);
    }

    // Current week's menu
    @GetMapping("/weekly")
    public ResponseEntity<Map<String, List<Map<String, Object>>>> getWeeklyLunchMenu() {
        Map<String, List<Map<String, Object>>> weeklyMenu = lunchService.getWeeklyLunch();
        return ResponseEntity.ok(weeklyMenu);
    }



    @RequestMapping(value = "/weekly", method = RequestMethod.POST)
    public ResponseEntity<Void> createWeeklyMenu(
            @RequestBody Map<String, List<Map<String, Object>>> weeklyMenu,
            @RequestParam("startOfWeek") String startOfWeek) {
        try {
            // Convert the startOfWeek string to LocalDate if needed
            LocalDate startDate = LocalDate.parse(startOfWeek);
            // Call your service method to create the weekly menu.
            // Ensure you have a corresponding method in your LunchService.
            lunchService.createWeeklyMenu(weeklyMenu, startDate);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    // Upcoming week's menu (next week)
    @GetMapping("/nextWeekly")
    public ResponseEntity<Map<String, List<Map<String, Object>>>> getNextWeeklyLunchMenu() {
        Map<String, List<Map<String, Object>>> nextWeeklyMenu = lunchService.getUpcomingWeekMenu();
        return ResponseEntity.ok(nextWeeklyMenu);
    }

    @PostMapping("/today")
    public ResponseEntity<Map<String, Object>> addLunchDish(@RequestBody Map<String, Object> request) {
        try {
            Map<String, Object> savedDish = lunchService.addLunchDish(request);
            return ResponseEntity.ok(savedDish);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

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

    @DeleteMapping("/today/{id}")
    public ResponseEntity<Void> deleteLunchDish(@PathVariable Long id) {
        lunchService.deleteLunchDish(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate-date")
    public ResponseEntity<Boolean> validateDateForUpcomingWeek(@RequestParam String date) {
        try {
            LocalDate requestedDate = LocalDate.parse(date);
            // Example validation using next week's range:
            LocalDate nextMonday = LocalDate.now().with(java.time.DayOfWeek.MONDAY).plusWeeks(1);
            LocalDate nextSunday = nextMonday.plusDays(6);
            boolean isValid = !requestedDate.isBefore(nextMonday) && !requestedDate.isAfter(nextSunday);
            return ResponseEntity.ok(isValid);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }
}
