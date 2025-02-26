package com.example.first_restaurant.controller.web;

import com.example.first_restaurant.entity.Booking;
import com.example.first_restaurant.service.BookingService;
import com.example.first_restaurant.service.DishService;
import com.example.first_restaurant.service.LunchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class WebController {

    @Autowired
    private LunchService lunchService;

    @Autowired
    private DishService dishService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/")
    public String home(Model model) {
        // Get lunch menus
        List<Map<String, Object>> todayLunch = dishService.getTodayLunch();
        Map<String, List<Map<String, Object>>> weeklyLunch = dishService.getWeeklyLunchMenu();

        model.addAttribute("todayLunch", todayLunch);
        model.addAttribute("weeklyLunch", weeklyLunch);
        model.addAttribute("today", LocalDate.now());

        // Get a la carte menu and categorize it
        List<Map<String, Object>> allDishes = dishService.getMenuByCategory();
        Map<String, List<Map<String, Object>>> categorizedMenu = new LinkedHashMap<>();

        for (Map<String, Object> dish : allDishes) {
            String typeName = (String) dish.get("DISH_TYPE_NAME");
            categorizedMenu.computeIfAbsent(typeName, k -> new ArrayList<>()).add(dish);
        }

        model.addAttribute("categorizedMenu", categorizedMenu);

        return "index";
    }

    // Simple booking endpoints for AJAX calls
    @GetMapping("/api/bookings/availability")
    @ResponseBody
    public List<String> getAvailability(@RequestParam String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        return bookingService.getAvailableTimeSlots(parsedDate);
    }

    @GetMapping("/api/bookings/booked-tables")
    @ResponseBody
    public Map<String, List<Integer>> getBookedTables(@RequestParam String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        return bookingService.getBookedTablesForDate(parsedDate);
    }

    @PostMapping("/api/bookings")
    @ResponseBody
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        try {
            Booking createdBooking = bookingService.createBooking(booking);
            return ResponseEntity.ok(createdBooking);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}