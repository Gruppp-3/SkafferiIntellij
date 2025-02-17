package com.example.first_restaurant.service;

import com.example.first_restaurant.entity.Order;
import com.example.first_restaurant.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getActiveOrders() {
        return orderRepository.findByOrderDateAndIsFinished(LocalDate.now(), false);
    }

    public void finishOrder(Long orderId) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setIsFinished(true);
            orderRepository.save(order);
        });
    }
}