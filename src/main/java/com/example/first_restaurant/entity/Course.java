package com.example.first_restaurant.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private Integer sequence;

    @OneToMany(mappedBy = "course")
    private List<MenuItem> menuItems;


}
