package com.example.first_restaurant.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "LUNCH_MENU")
public class LunchMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LUNCH_ID")
    private Long id;

    @Column(name = "LUNCH_DATE")
    private LocalDate lunchDate;

    @OneToMany(mappedBy = "lunchMenu", cascade = CascadeType.ALL)
    private List<LunchDish> lunchDishes = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getLunchDate() {
        return lunchDate;
    }

    public void setLunchDate(LocalDate lunchDate) {
        this.lunchDate = lunchDate;
    }

    public List<LunchDish> getLunchDishes() {
        return lunchDishes;
    }

    public void setLunchDishes(List<LunchDish> lunchDishes) {
        this.lunchDishes = lunchDishes;
    }

    // Getters and setters
}
