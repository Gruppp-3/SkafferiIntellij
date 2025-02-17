package com.example.first_restaurant.controller;
import com.example.first_restaurant.entity.Order;
import com.example.first_restaurant.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @GetMapping("/active")
    public ResponseEntity<List<Order>> getActiveOrders() {
        return ResponseEntity.ok(orderService.getActiveOrders());
    }

    @PutMapping("/{id}/finish")
    public ResponseEntity<Void> finishOrder(@PathVariable Long id) {
        orderService.finishOrder(id);
        return ResponseEntity.ok().build();
    }
}
