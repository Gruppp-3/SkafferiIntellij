package com.example.first_restaurant.service;
import com.example.first_restaurant.entity.Employee;
import com.example.first_restaurant.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


// Service Classes
@Service
@Transactional
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    // May be nullptr
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }
}