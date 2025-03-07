package com.example.first_restaurant.controller.api;

import com.example.first_restaurant.entity.WorkShift;
import com.example.first_restaurant.service.WorkShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/workshifts")
public class WorkShiftController {

    @Autowired
    private WorkShiftService workShiftService;

    @GetMapping
    public ResponseEntity<List<WorkShift>> getAllWorkShifts() {
        return ResponseEntity.ok(workShiftService.getAllWorkShifts());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<WorkShift>> getWorkShiftsByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(workShiftService.getWorkShiftsByEmployee(employeeId));
    }

    @GetMapping("/unassigned")
    public ResponseEntity<List<WorkShift>> getUnassignedWorkShifts() {
        return ResponseEntity.ok(workShiftService.getUnassignedWorkShifts());
    }

    @GetMapping("/date")
    public ResponseEntity<List<WorkShift>> getWorkShiftsByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        return ResponseEntity.ok(workShiftService.getWorkShiftsByDate(date));
    }

    @PostMapping
    public ResponseEntity<WorkShift> createWorkShift(@RequestBody WorkShift workShift) {
        return ResponseEntity.ok(workShiftService.createWorkShift(workShift));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkShift> updateWorkShift(@PathVariable Long id, @RequestBody WorkShift workShift) {
        return ResponseEntity.ok(workShiftService.updateWorkShift(id, workShift));
    }

    @PutMapping("/{id}/assign/{employeeId}")
    public ResponseEntity<WorkShift> assignShiftToEmployee(
            @PathVariable Long id, @PathVariable Long employeeId) {
        return ResponseEntity.ok(workShiftService.assignShiftToEmployee(id, employeeId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkShift(@PathVariable Long id) {
        workShiftService.deleteWorkShift(id);
        return ResponseEntity.ok().build();
    }
}