package com.example.first_restaurant.service;

import com.example.first_restaurant.entity.Employee;
import com.example.first_restaurant.entity.WorkShift;
import com.example.first_restaurant.repository.EmployeeRepository;
import com.example.first_restaurant.repository.WorkShiftRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WorkShiftService {

    @Autowired
    private WorkShiftRepository workShiftRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<WorkShift> getAllWorkShifts() {
        return workShiftRepository.findAll();
    }

    public List<WorkShift> getWorkShiftsByEmployee(Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            return workShiftRepository.findByEmployee(employee.get());
        }
        throw new EntityNotFoundException("Employee not found: " + employeeId);
    }

    public List<WorkShift> getUnassignedWorkShifts() {
        return workShiftRepository.findUnassignedShifts();
    }

    public List<WorkShift> getWorkShiftsByDate(LocalDateTime date) {
        return workShiftRepository.findShiftsByDate(date);
    }

    public WorkShift createWorkShift(WorkShift workShift) {
        // Set default status if not specified
        if (workShift.getShiftStatus() == null) {
            workShift.setShiftStatus(WorkShift.ShiftStatus.OPEN);
        }

        // If employee is assigned, update status accordingly
        if (workShift.getEmployee() != null) {
            workShift.setShiftStatus(WorkShift.ShiftStatus.ASSIGNED);
        }

        return workShiftRepository.save(workShift);
    }

    public WorkShift updateWorkShift(Long id, WorkShift updatedShift) {
        WorkShift existingShift = workShiftRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Work shift not found: " + id));

        existingShift.setStartTime(updatedShift.getStartTime());
        existingShift.setEndTime(updatedShift.getEndTime());
        existingShift.setDescription(updatedShift.getDescription());

        // Update employee and status
        if (updatedShift.getEmployee() != null) {
            existingShift.setEmployee(updatedShift.getEmployee());
            existingShift.setShiftStatus(WorkShift.ShiftStatus.ASSIGNED);
        } else {
            existingShift.setEmployee(null);
            existingShift.setShiftStatus(WorkShift.ShiftStatus.OPEN);
        }

        // Allow explicit status update if provided
        if (updatedShift.getShiftStatus() != null) {
            existingShift.setShiftStatus(updatedShift.getShiftStatus());
        }

        return workShiftRepository.save(existingShift);
    }

    public WorkShift assignShiftToEmployee(Long shiftId, Long employeeId) {
        WorkShift workShift = workShiftRepository.findById(shiftId)
                .orElseThrow(() -> new EntityNotFoundException("Work shift not found: " + shiftId));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + employeeId));

        workShift.setEmployee(employee);
        workShift.setShiftStatus(WorkShift.ShiftStatus.ASSIGNED);

        return workShiftRepository.save(workShift);
    }

    public void deleteWorkShift(Long id) {
        workShiftRepository.deleteById(id);
    }
}