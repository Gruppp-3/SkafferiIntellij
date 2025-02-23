package com.example.first_restaurant.repository;

import com.example.first_restaurant.entity.LunchDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LunchDishRepository extends JpaRepository<LunchDish, Long> {
    List<LunchDish> findByLunchMenuId(Long menuId);
}
