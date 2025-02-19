package com.example.first_restaurant.service;

import com.example.first_restaurant.entity.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Insert a new booking using a Booking object
    public int createBooking(Booking booking) {
        String sql = "INSERT INTO Booking (date, time, name, email, phone, people_count) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                booking.getBookingDate(),
                booking.getBookingTime(),
                booking.getCustomerName(),
                booking.getEmail(),
                booking.getPhone(),
                booking.getNumberOfGuests());
    }

    // Retrieve all bookings as a List<Booking>
    public List<Booking> getAllBookings() {
        String sql = "SELECT * FROM Booking";
        return jdbcTemplate.query(sql, new RowMapper<Booking>() {
            @Override
            public Booking mapRow(ResultSet rs, int rowNum) throws SQLException {
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("booking_id"));
                booking.setBookingDate(rs.getDate("date").toLocalDate());
                booking.setBookingTime(rs.getTime("time").toLocalTime());
                booking.setCustomerName(rs.getString("name"));
                booking.setEmail(rs.getString("email"));
                booking.setPhone(rs.getString("phone"));
                booking.setNumberOfGuests(rs.getInt("people_count"));
                booking.setCreatedAt(rs.getTimestamp("created_at"));
                return booking;
            }
        });
    }
}
