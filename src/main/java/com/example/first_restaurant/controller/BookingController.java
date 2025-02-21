package com.example.first_restaurant.controller;
import com.example.first_restaurant.entity.Booking;
import com.example.first_restaurant.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


// Hämtar data till webbsidan
@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    // Ta bort alla bokningar i databasen
    // curl.exe -X DELETE http://localhost:8080/api/bookings
    @DeleteMapping
    public ResponseEntity<Void> deleteAllBookings() {
        bookingService.deleteAllBookings();
        return ResponseEntity.ok().build();
    }

    // Your existing endpoints
    @GetMapping("/availability")
    public ResponseEntity<List<String>> getAvailableTimeSlots(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(bookingService.getAvailableTimeSlots(date));
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // Add new endpoint for getting booked tables
    @GetMapping("/booked-tables")
    public ResponseEntity<Map<String, List<Integer>>> getBookedTables(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(bookingService.getBookedTablesForDate(date));
    }
}
