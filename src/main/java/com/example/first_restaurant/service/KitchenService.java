package com.example.first_restaurant.service;

import com.example.first_restaurant.entity.Order;
import com.example.first_restaurant.entity.OrderDish;
import com.example.first_restaurant.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KitchenService {

    private static final Logger logger = LoggerFactory.getLogger(KitchenService.class);
    private final OrderRepository orderRepository;

    public KitchenService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Fetch all active orders for today's date.
     */
    public List<Map<String, Object>> getActiveOrders() {
        logger.debug("Fetching active orders for date: {}", LocalDate.now());
        List<Order> activeOrders = orderRepository.findByOrderDateAndIsFinished(LocalDate.now(), false);
        logger.debug("Found {} active orders", activeOrders.size());
        return activeOrders.stream()
                .map(order -> {
                    logger.debug("Processing order id: {}", order.getId());
                    return Map.of(
                            "orderId", order.getId(),
                            "tableNumber", order.getTableNumber(),
                            "status", order.getStatus(),
                            "dishes", order.getOrderDishes()
                    );
                })
                .collect(Collectors.toList());
    }

    /**
     * Update dish status (e.g., PENDING -> READY) and update overall order status accordingly.
     */
    @Transactional
    public Order updateOrderDishStatus(Long orderDishId, String newStatus) {
        logger.debug("Updating OrderDish id: {} to new status: {}", orderDishId, newStatus);
        Order order = orderRepository.findByOrderDishes_Id(orderDishId)
                .orElseThrow(() -> {
                    logger.error("Order not found for dish id: {}", orderDishId);
                    return new RuntimeException("Order not found for dish id: " + orderDishId);
                });
        logger.debug("Found Order id: {} for OrderDish id: {}", order.getId(), orderDishId);
        OrderDish dishToUpdate = order.getOrderDishes().stream()
                .filter(dish -> dish.getId().equals(orderDishId))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("OrderDish not found in Order id: {}", orderDishId);
                    return new RuntimeException("OrderDish not found in order: " + orderDishId);
                });
        logger.debug("Found OrderDish id: {}. Current status: {}", dishToUpdate.getId(), dishToUpdate.getStatus());

        dishToUpdate.setStatus(newStatus);
        logger.debug("Set OrderDish id: {} to new status: {}", dishToUpdate.getId(), newStatus);

        // Update overall order status based on individual dish statuses.
        boolean allDishesReady = order.getOrderDishes().stream()
                .allMatch(dish -> "READY".equalsIgnoreCase(dish.getStatus()));
        if (allDishesReady) {
            logger.debug("All dishes in Order id: {} are READY. Setting order status to COMPLETED", order.getId());
            order.setStatus("COMPLETED");
        }
        boolean allDishesServed = order.getOrderDishes().stream()
                .allMatch(dish -> "SERVED".equalsIgnoreCase(dish.getStatus()));
        if (allDishesServed) {
            logger.debug("All dishes in Order id: {} are SERVED. Setting order status to SERVED", order.getId());
            order.setStatus("SERVED");
        }

        Order savedOrder = orderRepository.save(order);
        logger.debug("Order id: {} saved with updated status: {}", savedOrder.getId(), savedOrder.getStatus());
        return savedOrder;
    }
}
