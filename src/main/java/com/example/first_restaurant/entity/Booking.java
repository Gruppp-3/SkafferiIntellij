package com.example.first_restaurant.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Timestamp;

public class Booking {

    private Integer bookingId;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private String customerName;
    private String email;
    private String phone;
    private int numberOfGuests;
    private Timestamp createdAt;

    // Getters and Setters
    public Integer getBookingId() {
        return bookingId;
    }
    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }
    public LocalDate getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }
    public LocalTime getBookingTime() {
        return bookingTime;
    }
    public void setBookingTime(LocalTime bookingTime) {
        this.bookingTime = bookingTime;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public int getNumberOfGuests() {
        return numberOfGuests;
    }
    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
