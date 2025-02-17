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

    @Column(name = "IS_DONE")
    private Boolean isDone;

    @Column(name = "IS_SERVED")
    private Boolean isServed;

    // Getters and Setters
}