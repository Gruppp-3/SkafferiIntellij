package com.example.first_restaurant.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ORDER_DISH")
public class OrderDish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_DISH_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "DISH_ID")
    private Dish dish;

    @Column(name = "NOTE_TEXT")
    private String noteText;

    // Added status field for tracking dish state (e.g., PENDING, READY, SERVED)
    @Column(name = "STATUS")
    private String status;

    // If you still need the booleans, you can keep them (or remove if not used)
    // @Column(name = "IS_DONE")
    // private Boolean isDone;
    // @Column(name = "IS_SERVED")
    // private Boolean isServed;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
