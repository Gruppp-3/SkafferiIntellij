package com.example.first_restaurant.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "WORK_SHIFT")
public class WorkShift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WORK_SHIFT_ID")
    private Long id;

    @Column(name = "START_TIME")
    private LocalDateTime startTime;

    @Column(name = "END_TIME")
    private LocalDateTime endTime;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    // Getters and Setters
}