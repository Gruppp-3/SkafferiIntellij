package com.example.first_restaurant.service;

import com.example.first_restaurant.entity.*;
import com.example.first_restaurant.repository.OrderRepository;
import com.example.first_restaurant.repository.DishRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sun.misc.Signal;

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
        Order order = new Order();
        order.setTableNumber(recOrder.getTableNumber());
        order.setOrderDate(LocalDate.now());
        order.setIsFinished(recOrder.getIsFinished());
        orderRepository.save(order);

        System.out.println(recOrder.getOrderSpecs().size());

        for (OrderSpecs specs : recOrder.getOrderSpecs()) {
            Dish dish = new Dish();
            dish.setOrderID(order.getId());
            dish.setCategory(specs.getCategory());
            dish.setDish_name(specs.getMeal());
            dish.setDish_count(specs.getCount());
            dish.setIsFinished(false);
            dishRepository.save(dish);
        }
        return order;
    }
    public void getSignalFromKitchen(Integer tableNr, Boolean starter, Boolean main, Boolean dessert){
        Order ord = orderRepository.findOrderByIsFinishedFalseAndTableNumber(tableNr);
        List<Dish> dishList = dishRepository.findAllByOrderID(ord.getId());
        System.out.println(starter);
        System.out.println(main);
        System.out.println(dessert);
        for(Dish dish : dishList){
            if("Förrätt".equalsIgnoreCase(dish.getCategory())){dish.setIsFinished(starter);}
            if("Huvudrätt".equalsIgnoreCase(dish.getCategory())){dish.setIsFinished(main);}
            if("Efterrätt".equalsIgnoreCase(dish.getCategory())){dish.setIsFinished(dessert);}
            dishRepository.save(dish);
        }
    }
    public List<RecievedOrder> sendToWaiter(){
        List<Order> orderList = orderRepository.findOrderByIsFinishedFalse();
        List<RecievedOrder> recievedOrderList = new ArrayList<>();
        for(Order ord : orderList){
            RecievedOrder order = new RecievedOrder();
            List<OrderSpecs> orderSpecs = new ArrayList<>();
            List<Dish> dishList = dishRepository.findAllByOrderID(ord.getId());
            for(Dish dish : dishList){
                if(!dish.getIsFinished()) continue;
                OrderSpecs orderspec = new OrderSpecs();
                orderspec.setCategory(dish.getCategory());
                orderspec.setMeal(dish.getDish_name());
                orderspec.setCount(dish.getDish_count());
                orderSpecs.add(orderspec);
            }
            order.setOrderSpecs(orderSpecs);
            order.setIsFinished(false);
            order.setTableNumber(ord.getTableNumber());
            recievedOrderList.add(order);
        }
        return recievedOrderList;
        /*
        List<SignalToWaiter> stwList = new ArrayList<>();
        for(Order ord : orderList){
            SignalToWaiter stw = new SignalToWaiter();
            stw.setTableNr(ord.getTableNumber());
            List<Dish> dishList = dishRepository.findAllByOrderID(ord.getId());
            for(Dish dish : dishList){
                if(dish.getIsFinished()){
                    if("Förrätt".equalsIgnoreCase(dish.getCategory())){stw.setStarter(true);}
                    if("Huvudrätt".equalsIgnoreCase(dish.getCategory())){stw.setMain(true);}
                    if("Efterrätt".equalsIgnoreCase(dish.getCategory())){stw.setDessert(true);}
                }
            }
            stwList.add(stw);
        }
        return stwList;*/
    }
    /**
     * Fetches all active (unfinished) orders and their associated dishes.
     *
     * @return A list of RecievedOrder objects containing order and dish details.
     */
    public List<RecievedOrder> getActiveOrders() {
        List<RecievedOrder> recievedOrdersList = new ArrayList<>();
        List<Order> orderList = orderRepository.findAllByIsFinishedIsFalse();

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


    // Markera en order som levererad
    public boolean markOrderAsDelivered(int tableNumber) {
        Optional<Order> order = orderRepository.findByTableNumberAndIsFinishedFalse(tableNumber);

        if (order.isPresent()) {
            Order updatedOrder = order.get();
            updatedOrder.setIsFinished(true);
            orderRepository.save(updatedOrder);
            return true;
        } else {
            return false;
        }
    }
}
