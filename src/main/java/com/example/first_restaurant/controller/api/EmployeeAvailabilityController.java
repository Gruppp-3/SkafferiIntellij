package com.example.first_restaurant.controller.api;

import com.example.first_restaurant.entity.Employee;
import com.example.first_restaurant.entity.EmployeeAvailability;
import com.example.first_restaurant.service.EmployeeAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/availability")
public class EmployeeAvailabilityController {

    @Autowired
    private EmployeeAvailabilityService availabilityService;

    @GetMapping
    public ResponseEntity<List<EmployeeAvailability>> getAllAvailability() {
        return ResponseEntity.ok(availabilityService.getAllAvailability());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EmployeeAvailability>> getEmployeeAvailability(@PathVariable Long employeeId) {
        return ResponseEntity.ok(availabilityService.getEmployeeAvailability(employeeId));
    }

    @PostMapping
    public ResponseEntity<EmployeeAvailability> createAvailability(@RequestBody EmployeeAvailability availability) {
        return ResponseEntity.ok(availabilityService.createAvailability(availability));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeAvailability> updateAvailability(
            @PathVariable Long id,
            @RequestBody EmployeeAvailability availability) {
        return ResponseEntity.ok(availabilityService.updateAvailability(id, availability));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvailability(@PathVariable Long id) {
        availabilityService.deleteAvailability(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/employees/available")
    public ResponseEntity<List<Employee>> getAvailableEmployees(
            @RequestParam DayOfWeek dayOfWeek,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {
        return ResponseEntity.ok(availabilityService.getAvailableEmployees(dayOfWeek, startTime, endTime));
    }
}