package com.example.first_restaurant.service;

import com.example.first_restaurant.entity.OrderSpecs;
import com.example.first_restaurant.entity.Order;
import com.example.first_restaurant.entity.Dish;
import com.example.first_restaurant.entity.RecievedOrder;
import com.example.first_restaurant.repository.OrderRepository;
import com.example.first_restaurant.repository.DishRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class KitchenService {

    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;

    public KitchenService(OrderRepository orderRepository, DishRepository dishRepository) {
        this.orderRepository = orderRepository;
        this.dishRepository = dishRepository;
    }

    /**
     * Saves an order received from the waiter.
     *
     * @param recOrder The received order details.
     * @return The saved Order object.
     */
    public Order saveOrder(RecievedOrder recOrder) {
        if (recOrder == null || recOrder.getOrderSpecs() == null || recOrder.getOrderSpecs().isEmpty()) {
            throw new IllegalArgumentException("RecievedOrder eller orderSpecs är null/tomt!");
        }

        Order order = new Order();
        order.setTableNumber(recOrder.getTableNumber());
        order.setOrderDate(LocalDate.now());
        order.setIsFinished(recOrder.getIsFinished());

        try {
            order = orderRepository.save(order);
            System.out.println("Order sparad: ID = " + order.getId() + ", Bord = " + order.getTableNumber());
        } catch (Exception e) {
            System.err.println("Fel vid sparande av order i databasen: " + e.getMessage());
            throw new RuntimeException("Databasfel vid sparande av order", e);
        }

        System.out.println("Antal beställda rätter: " + recOrder.getOrderSpecs().size());

        for (OrderSpecs specs : recOrder.getOrderSpecs()) {
            if (specs.getMeal() == null || specs.getMeal().trim().isEmpty()) {
                System.err.println("Varning: Tom maträttsnamn hittades i orderSpecs! Skippas.");
                continue; // Hoppa över tomma rätter
            }

            Dish dish = new Dish();
            dish.setOrderID(order.getId());
            dish.setCategory(specs.getCategory());
            dish.setDish_name(specs.getMeal());
            dish.setDish_count(specs.getCount());

            try {
                dishRepository.save(dish);
                System.out.println("Rätt sparad: " + dish.getDish_name() + " (Kategori: " + dish.getCategory() + ", Antal: " + dish.getDish_count() + ")");
            } catch (Exception e) {
                System.err.println("Fel vid sparande av rätt i databasen: " + e.getMessage());
            }
        }

        return order;
    }

    /**
     * Fetches all active (unfinished) orders and their associated dishes.
     *
     * @return A list of RecievedOrder objects containing order and dish details.
     */
    public List<RecievedOrder> getActiveOrders() {
        List<RecievedOrder> recievedOrdersList = new ArrayList<>();
        List<Order> orderList = orderRepository.findAllByIsFinishedIsFalse();

        if (orderList.isEmpty()) {
            System.out.println("Inga aktiva beställningar hittades.");
        }

        for (Order order : orderList) {
            List<Dish> dishList = dishRepository.findAllByOrderID(order.getId());

            System.out.println("Order från bord " + order.getTableNumber() + " innehåller: " + dishList.size() + " rätter.");

            RecievedOrder recievedOrder = new RecievedOrder();
            recievedOrder.setTableNumber(order.getTableNumber());
            recievedOrder.setIsFinished(order.getIsFinished());
            recievedOrder.setOrderSpecs(new ArrayList<>());

            for (Dish dish : dishList) {
                OrderSpecs orderSpecs = new OrderSpecs();
                orderSpecs.setCategory(dish.getCategory());
                orderSpecs.setMeal(dish.getDish_name());
                orderSpecs.setCount(dish.getDish_count());

                System.out.println("Lägger till rätt: " + dish.getDish_name() + " (Kategori: " + dish.getCategory() + ", Antal: " + dish.getDish_count() + ")");

                recievedOrder.getOrderSpecs().add(orderSpecs);
            }

            recievedOrdersList.add(recievedOrder);
        }

        System.out.println("Totalt skickade beställningar: " + recievedOrdersList.size());
        return recievedOrdersList;
    }
}
