package com.example.first_restaurant.repository;

import com.example.first_restaurant.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrderByIsFinishedFalse();
    List<Order> findAllByIsFinishedIsFalse();
    Order findOrderByIsFinishedFalseAndTableNumber(Integer tableNr);
}
