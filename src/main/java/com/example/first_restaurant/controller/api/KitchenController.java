package com.example.first_restaurant.controller.api;
import com.example.first_restaurant.entity.Order;
import com.example.first_restaurant.entity.RecievedOrder;
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
}
