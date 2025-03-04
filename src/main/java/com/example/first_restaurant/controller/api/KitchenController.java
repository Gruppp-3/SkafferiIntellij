package com.example.first_restaurant.controller.api;
import com.example.first_restaurant.entity.Order;
import com.example.first_restaurant.entity.RecievedOrder;
import com.example.first_restaurant.entity.SignalToWaiter;
import com.example.first_restaurant.service.KitchenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kitchen")
public class KitchenController {

    private final KitchenService kitchenService;

    public KitchenController(KitchenService kitchenService) {
        this.kitchenService = kitchenService;
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> sendOrders(@RequestBody RecievedOrder recOrder) {
        return ResponseEntity.ok(kitchenService.saveOrder(recOrder));
    }
    @GetMapping("/orders")
    public ResponseEntity<List<RecievedOrder>> getActiveOrders() {
        return ResponseEntity.ok(kitchenService.getActiveOrders());
    }
    @PutMapping("/order-dish/{orderDishId}/status")
    public ResponseEntity<Void> updateOrderDishStatus(
            @PathVariable Long orderDishId, @RequestParam String status) {
        //kitchenService.updateOrderDishStatus(orderDishId, status);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/signal")
    public void getSignalFromKitchen(@RequestParam("tableNr") Integer tableNr, @RequestParam("starter")Boolean starter, @RequestParam("main") Boolean main, @RequestParam("dessert") Boolean dessert){
        kitchenService.getSignalFromKitchen(tableNr, starter, main, dessert);
    }
    @GetMapping("/orders/ready")
    public List<RecievedOrder> SendSignalToWaiter(){
        return kitchenService.sendToWaiter();
    }

    // PUT-metod för att markera en order som levererad
    @PutMapping("/orders/{tableNumber}/delivered")
    public ResponseEntity<Void> markOrderDelivered(@PathVariable("tableNumber") int tableNumber) {
        boolean isUpdated = kitchenService.markOrderAsDelivered(tableNumber);

        if (isUpdated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }
}

