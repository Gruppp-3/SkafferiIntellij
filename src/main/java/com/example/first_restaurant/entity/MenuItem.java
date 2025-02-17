package com.example.first_restaurant.entity;
import jakarta.persistence.*;


@Entity
@Table(name = "menu_items")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String category;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
