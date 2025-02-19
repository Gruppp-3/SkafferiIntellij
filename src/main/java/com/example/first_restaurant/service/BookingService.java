package com.example.first_restaurant.service;

import com.example.first_restaurant.entity.Booking;
import com.example.first_restaurant.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingService {

    public List<Booking> getAllBookings() {
        return bookingRepository.findAllByOrderByDateAscTimeAsc();
    }

    @Autowired
    private BookingRepository bookingRepository;

    public List<String> getAvailableTimeSlots(LocalDate date) {
        List<String> allTimeSlots = Arrays.asList(
                "16:00", "16:30", "17:00", "17:30", "18:00", "18:30",
                "19:00", "19:30", "20:00", "20:30", "21:00", "21:30",
                "22:00", "22:30", "23:00"
        );

        // Get all bookings for the given date
        List<Booking> existingBookings = bookingRepository.findByDate(date);

        // Convert booked times to strings for comparison
        Set<String> bookedTimes = existingBookings.stream()
                .map(booking -> booking.getTime().toString().substring(0, 5))
                .collect(Collectors.toSet());

        return allTimeSlots.stream()
                .filter(time -> !bookedTimes.contains(time))
                .collect(Collectors.toList());
    }

    public Booking createBooking(Booking booking) {

        // Check if timeslot is available
        List<Booking> existingBookings = bookingRepository
                .findByDateAndTime(booking.getDate(), booking.  getTime());

        if (!existingBookings.isEmpty()) {
            throw new RuntimeException("This time slot is already booked");
        }

        // Save and return the booking
        return bookingRepository.save(booking);
    }
}