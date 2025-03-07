package com.example.first_restaurant.repository;

import com.example.first_restaurant.entity.Employee;
import com.example.first_restaurant.entity.WorkShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WorkShiftRepository extends JpaRepository<WorkShift, Long> {

    List<WorkShift> findByEmployee(Employee employee);

    // Find all unassigned shifts
    @Query("SELECT ws FROM WorkShift ws WHERE ws.employee IS NULL OR ws.shiftStatus = 'OPEN'")
    List<WorkShift> findUnassignedShifts();

    // Find shifts for a specific day
    @Query("SELECT ws FROM WorkShift ws WHERE DATE(ws.startTime) = DATE(:date)")
    List<WorkShift> findShiftsByDate(LocalDateTime date);

    // Find shifts between two dates
    List<WorkShift> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
}