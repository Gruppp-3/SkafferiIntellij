package com.example.first_restaurant.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "LUNCH_DISH")
public class LunchDish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LUNCH_DISH_ID")
    private Long id;

    @Column(name = "LUNCH_DISH_NAME")
    private String name;

    @Column(name = "LUNCH_DISH_DESCRIPTION")
    private String description;

    @Column(name = "LUNCH_DISH_PRICE")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "LUNCH_ID")
    private LunchMenu lunchMenu;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LunchMenu getLunchMenu() {
        return lunchMenu;
    }

    public void setLunchMenu(LunchMenu lunchMenu) {
        this.lunchMenu = lunchMenu;
    }

}