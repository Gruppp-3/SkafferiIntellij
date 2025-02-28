package com.example.first_restaurant.repository;

import com.example.first_restaurant.entity.LunchMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LunchMenuRepository extends JpaRepository<LunchMenu, Long> {
    Optional<LunchMenu> findByLunchDate(LocalDate date);
    List<LunchMenu> findByLunchDateBetween(LocalDate startDate, LocalDate endDate);


    default List<LunchMenu> findWeeklyMenus(LocalDate startOfWeek) {
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        return findByLunchDateBetween(startOfWeek, endOfWeek);
    }
}
