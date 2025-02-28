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

    @Autowired
    private BookingRepository bookingRepository;

    /**
     * Hämta alla bokningar, sorterade efter datum och tid
     */
    public List<Booking> getAllBookings() {
        return bookingRepository.findAllByOrderByDateAscTimeAsc();
    }

    /**
     * Hämta tillgängliga tider för ett specifikt datum
     */
    public List<String> getAvailableTimeSlots(LocalDate date) {
        List<Booking> existingBookings = bookingRepository.findByDate(date);

        Set<String> bookedTimes = existingBookings.stream()
                .map(booking -> formatTime(booking.getTime()))
                .collect(Collectors.toSet());

        return ALL_TIME_SLOTS.stream()
                .filter(time -> !bookedTimes.contains(time))
                .collect(Collectors.toList());
    }

    /**
     * Skapa en ny bokning
     */
    public Booking createBooking(Booking booking) {
        List<Booking> existingBookings = bookingRepository
                .findByDateAndTime(booking.getDate(), booking.getTime());

        if (!existingBookings.isEmpty()) {
            throw new RuntimeException("Denna tid är redan bokad. Välj en annan tid.");
        }

        return bookingRepository.save(booking);
    }

    /**
     * Radera alla bokningar
     */
    public void deleteAllBookings() {
        bookingRepository.deleteAll();
    }

    /**
     * Hämta en bokning via ID
     */
    public Booking getBookingById(Integer id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Bokning ej hittad med ID: " + id));
    }

    /**
     * Radera en bokning via ID
     */
    public void deleteBooking(Integer id) {
        Booking booking = getBookingById(id);
        bookingRepository.delete(booking);
    }

    /**
     * Uppdatera en bokning
     */
    public Booking updateBooking(Integer id, Booking bookingDetails) {
        Booking existingBooking = getBookingById(id);
        updateBookingFields(existingBooking, bookingDetails);
        return bookingRepository.save(existingBooking);
    }

    /**
     * Hjälpmetod för att uppdatera bokningsfält
     */
    private void updateBookingFields(Booking target, Booking source) {
        if (source.getName() != null) target.setName(source.getName());
        if (source.getEmail() != null) target.setEmail(source.getEmail());
        if (source.getPhone() != null) target.setPhone(source.getPhone());
        if (source.getDate() != null) target.setDate(source.getDate());
        if (source.getTime() != null) target.setTime(source.getTime());
        if (source.getPeopleCount() != null) target.setPeopleCount(source.getPeopleCount());
    }

    /**
     * Formatera LocalTime till HH:mm
     */
    private String formatTime(LocalTime time) {
        return time.toString().substring(0, 5);
    }
}
