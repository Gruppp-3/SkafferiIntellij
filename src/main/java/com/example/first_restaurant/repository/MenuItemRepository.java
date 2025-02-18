package com.example.first_restaurant.repository;

import com.example.first_restaurant.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByCategory(String category);
    List<MenuItem> findByCourseId(Long courseId);
    List<MenuItem> findByCategoryAndCourseId(String category, Long courseId);
}