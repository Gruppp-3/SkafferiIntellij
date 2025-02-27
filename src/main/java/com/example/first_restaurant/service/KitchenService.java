package com.example.first_restaurant.service;

import com.example.first_restaurant.entity.Order;
import com.example.first_restaurant.entity.Dish;
import com.example.first_restaurant.entity.OrderDish;
import com.example.first_restaurant.repository.OrderRepository;
import com.example.first_restaurant.repository.DishRepository;
import com.example.first_restaurant.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KitchenService {

    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;


    public KitchenService(OrderRepository orderRepository, DishRepository dishRepository) {
        this.orderRepository = orderRepository;
        this.dishRepository = dishRepository;
    }

    /**
     * Fetch all active orders for today's date.
     */
    public List<Map<String, Object>> getActiveOrders() {
        List<Order> activeOrders = orderRepository.findByOrderDateAndIsFinished(LocalDate.now(), false);
        return activeOrders.stream()
                .map(order -> Map.of(
                        "orderId", order.getId(),
                        "tableNumber", order.getTableNumber(),
                        "dishes", order.getOrderDishes()
                ))
                .collect(Collectors.toList());
    }
}
    /**
     * Update dish status (e.g., PENDING -> READY).
     */
    /**

    /*
    @Transactional
    public void updateOrderDishStatus(Long orderDishId, String status) {

        OrderDish orderDish = orderRepository.findById(orderDishId)
                .orElseThrow(() -> new RuntimeException("OrderDish not found"));

        orderDish.setStatus(status);
        orderRepository.save(orderDish);
    }
} */
