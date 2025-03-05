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
        List<Map<String, Object>> todayLunch = dishService.getTodayLunch();
        Map<String, List<Map<String, Object>>> weeklyLunch = dishService.getWeeklyLunchMenu();

        model.addAttribute("todayLunch", todayLunch);
        model.addAttribute("weeklyLunchMenu", weeklyLunch);
        model.addAttribute("today", LocalDate.now());

        List<Map<String, Object>> allDishes = dishService.getMenuByCategory();
        Map<String, List<Map<String, Object>>> categorizedMenu = new LinkedHashMap<>();

        for (Map<String, Object> dish : allDishes) {
            String typeName = (String) dish.get("DISH_TYPE_NAME");
            categorizedMenu.computeIfAbsent(typeName, k -> new ArrayList<>()).add(dish);
        }

        model.addAttribute("categorizedMenu", categorizedMenu);
        return "index";
    }

    @GetMapping("/api/bookings/availability")
    @ResponseBody
    public List<String> getAvailability(@RequestParam String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        return bookingService.getAvailableTimeSlots(parsedDate);
    }

    @PostMapping("/api/bookings")
    @ResponseBody
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        System.out.println("Received Booking Request: " + booking); // Debug log

        LocalDate today = LocalDate.now();
        if (!booking.getDate().isAfter(today)) {
            System.out.println("Booking failed: Date is not at least 24h ahead.");
            return ResponseEntity.badRequest().body(Map.of("message", "Bokningar måste göras minst 24 timmar i förväg."));
        }

        try {
            Booking createdBooking = bookingService.createBooking(booking);
            System.out.println("Booking successful: " + createdBooking); // Debug log
            return ResponseEntity.ok(createdBooking);
        } catch (Exception e) {
            System.out.println("Booking error: " + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

}
