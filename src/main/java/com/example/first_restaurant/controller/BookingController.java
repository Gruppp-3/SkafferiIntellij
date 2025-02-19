package com.example.first_restaurant.controller;

import com.example.first_restaurant.entity.Booking;
import com.example.first_restaurant.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Create a new booking from JSON request body
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Booking createBooking(@RequestBody Booking booking) {
        bookingService.createBooking(booking);
        return booking;
    }

    // Retrieve all bookings
    @GetMapping
    public List<Booking> getBookings() {
        return bookingService.getAllBookings();
    }
}
