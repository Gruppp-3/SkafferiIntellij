package com.example.first_restaurant.service;

import com.example.first_restaurant.entity.Employee;
import com.example.first_restaurant.entity.EmployeeAvailability;
import com.example.first_restaurant.repository.EmployeeAvailabilityRepository;
import com.example.first_restaurant.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class EmployeeAvailabilityService {

    @Autowired
    private EmployeeAvailabilityRepository availabilityRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<EmployeeAvailability> getAllAvailability() {
        return availabilityRepository.findAll();
    }

    public List<EmployeeAvailability> getEmployeeAvailability(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + employeeId));
        return availabilityRepository.findByEmployee(employee);
    }

    public EmployeeAvailability createAvailability(EmployeeAvailability availability) {
        if (availability.getEmployee() == null || availability.getEmployee().getId() == null) {
            throw new IllegalArgumentException("Employee must be specified");
        }

        Employee employee = employeeRepository.findById(availability.getEmployee().getId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: "
                        + availability.getEmployee().getId()));

        availability.setEmployee(employee);
        return availabilityRepository.save(availability);
    }

    public EmployeeAvailability updateAvailability(Long id, EmployeeAvailability updatedAvailability) {
        EmployeeAvailability existingAvailability = availabilityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Availability not found: " + id));

        existingAvailability.setDayOfWeek(updatedAvailability.getDayOfWeek());
        existingAvailability.setStartTime(updatedAvailability.getStartTime());
        existingAvailability.setEndTime(updatedAvailability.getEndTime());
        existingAvailability.setPreference(updatedAvailability.getPreference());

        return availabilityRepository.save(existingAvailability);
    }

    public void deleteAvailability(Long id) {
        availabilityRepository.deleteById(id);
    }

    public List<Employee> getAvailableEmployees(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        return availabilityRepository.findEmployeesAvailableForTimeRange(dayOfWeek, startTime, endTime);
    }
}