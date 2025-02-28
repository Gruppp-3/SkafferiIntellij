package com.example.first_restaurant.repository;

import com.example.first_restaurant.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findAllByOrderID(Long order_id);
}

