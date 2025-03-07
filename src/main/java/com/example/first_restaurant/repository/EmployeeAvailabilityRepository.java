package com.example.first_restaurant.repository;

import com.example.first_restaurant.entity.Employee;
import com.example.first_restaurant.entity.EmployeeAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface EmployeeAvailabilityRepository extends JpaRepository<EmployeeAvailability, Long> {

    List<EmployeeAvailability> findByEmployee(Employee employee);

    List<EmployeeAvailability> findByDayOfWeek(DayOfWeek dayOfWeek);

    // Find all availabilities for a given employee and day of week
    List<EmployeeAvailability> findByEmployeeAndDayOfWeek(Employee employee, DayOfWeek dayOfWeek);

    // Find employees available at a specific time on a specific day
    @Query("SELECT ea.employee FROM EmployeeAvailability ea " +
            "WHERE ea.dayOfWeek = :dayOfWeek " +
            "AND ea.startTime <= :time " +
            "AND ea.endTime >= :time " +
            "AND ea.preference != 'UNAVAILABLE'")
    List<Employee> findAvailableEmployees(@Param("dayOfWeek") DayOfWeek dayOfWeek,
                                          @Param("time") LocalTime time);

    // Find employees available for a specific time range on a specific day
    @Query("SELECT ea.employee FROM EmployeeAvailability ea " +
            "WHERE ea.dayOfWeek = :dayOfWeek " +
            "AND ea.startTime <= :startTime " +
            "AND ea.endTime >= :endTime " +
            "AND ea.preference != 'UNAVAILABLE'")
    List<Employee> findEmployeesAvailableForTimeRange(
            @Param("dayOfWeek") DayOfWeek dayOfWeek,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime);
}