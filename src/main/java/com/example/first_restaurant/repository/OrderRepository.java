package com.example.first_restaurant.repository;

import com.example.first_restaurant.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderDateAndIsFinished(LocalDate orderDate, Boolean isFinished);

    // NEW FUNCTION FOR KITCHEN APP
    Optional<Order> findByOrderDishes_Id(Long orderDishId);
}
