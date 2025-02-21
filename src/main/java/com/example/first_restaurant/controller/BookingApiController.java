package com.example.first_restaurant.controller;
import com.example.first_restaurant.entity.Booking;
import com.example.first_restaurant.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingApiController {
    private static final Logger logger = LoggerFactory.getLogger(BookingApiController.class);

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        logger.info("Received request to get all bookings");
        List<Booking> bookings = bookingService.getAllBookings();
        logger.info("Returning {} bookings", bookings.size());
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable Integer id) {
        logger.info("Received request to get booking with id: {}", id);
        Booking booking = bookingService.getBookingById(id);
        logger.info("Found booking: {}", booking);
        return ResponseEntity.ok(booking);
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        logger.info("Received request to create booking: {}", booking);
        Booking createdBooking = bookingService.createBooking(booking);
        logger.info("Created booking with id: {}", createdBooking.getBookingId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Integer id,
                                                 @RequestBody Booking booking) {
        logger.info("Received request to update booking with id: {}", id);
        Booking updatedBooking = bookingService.updateBooking(id, booking);
        logger.info("Updated booking: {}", updatedBooking);
        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Integer id) {
        logger.info("Received request to delete booking with id: {}", id);
        bookingService.deleteBooking(id);
        logger.info("Deleted booking with id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        logger.error("Error handling request: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getReason(), ex.getStatusCode());
    }
}