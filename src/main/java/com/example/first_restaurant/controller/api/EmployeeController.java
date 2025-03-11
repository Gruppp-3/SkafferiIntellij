package com.example.first_restaurant.controller.api;

import com.example.first_restaurant.entity.Employee;
import com.example.first_restaurant.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // La till felhantering
    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        try {
            // Check if ID is provided
            if (employee.getId() == null) {
                return ResponseEntity.badRequest().body("Employee ID must be provided");
            }

            // Check if an employee with this ID already exists
            if (employeeService.getEmployeeById(employee.getId()).isPresent()) {
                return ResponseEntity.badRequest().body("An employee with this ID already exists");
            }

            Employee savedEmployee = employeeService.saveEmployee(employee);
            return ResponseEntity.ok(savedEmployee);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error creating employee: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Optional<Employee> existingEmployee = employeeService.getEmployeeById(id);

        if (existingEmployee.isPresent()) {
            // Ensure ID is set correctly
            employee.setId(id);
            Employee updatedEmployee = employeeService.saveEmployee(employee);
            return ResponseEntity.ok(updatedEmployee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        try {
            Optional<Employee> employee = employeeService.getEmployeeById(id);
            if (employee.isPresent()) {
                employeeService.deleteEmployee(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error deleting employee: " + e.getMessage());
        }
    }

    // Login for staff app
    @GetMapping("/verify/{id}")
    public ResponseEntity<Boolean> verifyEmployeeId(@PathVariable Long id) {
        boolean exists = employeeService.getEmployeeById(id).isPresent();
        return ResponseEntity.ok(exists);
    }
}