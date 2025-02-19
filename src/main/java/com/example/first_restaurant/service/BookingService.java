package com.example.first_restaurant.service;

import com.example.first_restaurant.entity.Booking;
import com.example.first_restaurant.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingService {

    public List<Booking> getAllBookings() {
        return bookingRepository.findAllByOrderByDateAscTimeAsc();
    }

    @Autowired
    private BookingRepository bookingRepository;

    public List<String> getAvailableTimeSlots(LocalDate date) {
        // Define all possible time slots
        List<String> allTimeSlots = Arrays.asList(
                "16:00", "16:30", "17:00", "17:30", "18:00", "18:30",
                "19:00", "19:30", "20:00", "20:30", "21:00", "21:30",
                "22:00", "22:30", "23:00"
        );

        // Get bookings for the date
        List<Booking> existingBookings = bookingRepository.findByDate(date);

        // Count how many tables are booked for each time
        Map<String, Long> bookedTablesCount = existingBookings.stream()
                .collect(Collectors.groupingBy(
                        booking -> booking.getTime().toString().substring(0, 5),
                        Collectors.counting()
                ));

        // Total number of tables
        final int TOTAL_TABLES = 6; // Assuming you have 6 tables

        // Return time slots where not all tables are booked
        return allTimeSlots.stream()
                .filter(time -> {
                    Long bookedTables = bookedTablesCount.getOrDefault(time, 0L);
                    return bookedTables < TOTAL_TABLES; // Time is available if at least one table is free
                })
                .collect(Collectors.toList());
    }

    public Map<String, List<Integer>> getBookedTablesForDate(LocalDate date) {
        List<Booking> bookings = bookingRepository.findByDate(date);
        Map<String, List<Integer>> bookedTables = new HashMap<>();

        for (Booking booking : bookings) {
            String timeKey = booking.getTime().toString().substring(0, 5);
            if (!bookedTables.containsKey(timeKey)) {
                bookedTables.put(timeKey, new ArrayList<>());
            }
            bookedTables.get(timeKey).add(booking.getTableNumber());
        }

        return bookedTables;
    }

    public Booking createBooking(Booking booking) {
        // Check if table is already booked
        List<Booking> existingBookings = bookingRepository
                .findByDateAndTimeAndTableNumber(
                        booking.getDate(),
                        booking.getTime(),
                        booking.getTableNumber()
                );

        if (!existingBookings.isEmpty()) {
            throw new RuntimeException("This table is already booked for the selected time");
        }

        return bookingRepository.save(booking);
    }
    public void deleteAllBookings() {
        bookingRepository.deleteAll();
    }
    public Booking getBookingById(Integer id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            return booking.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Booking not found with id: " + id
            );
        }
    }

    public void deleteBooking(Integer id) {
        Booking booking = getBookingById(id);
        bookingRepository.delete(booking);
    }

    public Booking updateBooking(Integer id, Booking bookingDetails) {
        Booking existingBooking = getBookingById(id);

        // Update fields using the correct names from your entity
        if (bookingDetails.getTableNumber() != null) {
            existingBooking.setTableNumber(bookingDetails.getTableNumber());
        }
        if (bookingDetails.getName() != null) {
            existingBooking.setName(bookingDetails.getName());
        }
        if (bookingDetails.getEmail() != null) {
            existingBooking.setEmail(bookingDetails.getEmail());
        }
        if (bookingDetails.getPhone() != null) {
            existingBooking.setPhone(bookingDetails.getPhone());
        }
        if (bookingDetails.getDate() != null) {
            existingBooking.setDate(bookingDetails.getDate());
        }
        if (bookingDetails.getTime() != null) {
            existingBooking.setTime(bookingDetails.getTime());
        }
        if (bookingDetails.getPeopleCount() != null) {
            existingBooking.setPeopleCount(bookingDetails.getPeopleCount());
        }

        return bookingRepository.save(existingBooking);
    }

}