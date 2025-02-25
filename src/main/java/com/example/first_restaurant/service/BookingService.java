package com.example.first_restaurant.service;

import com.example.first_restaurant.entity.Booking;
import com.example.first_restaurant.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private static final List<String> ALL_TIME_SLOTS = Arrays.asList(
            "16:00", "16:30", "17:00", "17:30", "18:00", "18:30",
            "19:00", "19:30", "20:00", "20:30", "21:00", "21:30",
            "22:00", "22:30", "23:00"
    );
    private static final int TOTAL_TABLES = 6; // Configuration value

    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAllByOrderByDateAscTimeAsc();
    }

    public List<String> getAvailableTimeSlots(LocalDate date) {
        // Get bookings for the date
        List<Booking> existingBookings = bookingRepository.findByDate(date);

        // Count how many tables are booked for each time
        Map<String, Long> bookedTablesCount = existingBookings.stream()
                .collect(Collectors.groupingBy(
                        booking -> formatTime(booking.getTime()),
                        Collectors.counting()
                ));

        // Return time slots where not all tables are booked
        return ALL_TIME_SLOTS.stream()
                .filter(time -> bookedTablesCount.getOrDefault(time, 0L) < TOTAL_TABLES)
                .collect(Collectors.toList());
    }

    public Map<String, List<Integer>> getBookedTablesForDate(LocalDate date) {
        List<Booking> bookings = bookingRepository.findByDate(date);
        Map<String, List<Integer>> bookedTables = new HashMap<>();

        for (Booking booking : bookings) {
            String timeKey = formatTime(booking.getTime());
            bookedTables.computeIfAbsent(timeKey, k -> new ArrayList<>())
                    .add(booking.getTableNumber());
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
        updateBookingFields(existingBooking, bookingDetails);
        return bookingRepository.save(existingBooking);
    }

    // Helper methods
    private void updateBookingFields(Booking target, Booking source) {
        if (source.getTableNumber() != null) target.setTableNumber(source.getTableNumber());
        if (source.getName() != null) target.setName(source.getName());
        if (source.getEmail() != null) target.setEmail(source.getEmail());
        if (source.getPhone() != null) target.setPhone(source.getPhone());
        if (source.getDate() != null) target.setDate(source.getDate());
        if (source.getTime() != null) target.setTime(source.getTime());
        if (source.getPeopleCount() != null) target.setPeopleCount(source.getPeopleCount());
    }

    private String formatTime(LocalTime time) {
        return time.toString().substring(0, 5);
    }
}