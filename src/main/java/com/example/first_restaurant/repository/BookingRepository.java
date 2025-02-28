package com.example.first_restaurant.repository;

import com.example.first_restaurant.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findByDate(LocalDate date);

    List<Booking> findByDateAndTime(LocalDate date, LocalTime time);

    List<Booking> findAllByOrderByDateAscTimeAsc();
}
