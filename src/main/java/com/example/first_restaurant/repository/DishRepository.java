package com.example.first_restaurant.repository;

import com.example.first_restaurant.entity.Dish;
import com.example.first_restaurant.entity.DishType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findByDishType(DishType dishType);
}

