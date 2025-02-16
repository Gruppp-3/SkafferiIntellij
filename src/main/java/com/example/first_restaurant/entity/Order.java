package com.example.first_restaurant.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "table_number")
    private Integer tableNumber;

    private String status;

    private LocalDateTime timestamp;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    // Getters and setters
}
