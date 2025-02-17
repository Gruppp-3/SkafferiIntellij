package com.example.first_restaurant.entity;
import jakarta.persistence.*;

import java.util.List;

// Entity Classes
@Entity
@Table(name = "EMPLOYEE")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_ID")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "IS_ADMIN")
    private Boolean isAdmin;

    @OneToMany(mappedBy = "employee")
    private List<WorkShift> workShifts;

    // Getters and Setters
}