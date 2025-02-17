package com.example.first_restaurant.repository;

import com.example.first_restaurant.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository <Employee, Long> {
        List<Employee> findByIsAdmin(Boolean isAdmin);
    }
