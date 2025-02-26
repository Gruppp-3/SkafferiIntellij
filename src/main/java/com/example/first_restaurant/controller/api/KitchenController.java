package com.example.first_restaurant.controller.api;

import com.example.first_restaurant.service.KitchenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/kitchen")
public class KitchenController {

    private final KitchenService kitchenService;

    public KitchenController(KitchenService kitchenService) {
        this.kitchenService = kitchenService;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Map<String, Object>>> getActiveOrders() {
        return ResponseEntity.ok(kitchenService.getActiveOrders());
    }

    @PutMapping("/order-dish/{orderDishId}/status")
    public ResponseEntity<Void> updateOrderDishStatus(
            @PathVariable Long orderDishId, @RequestParam String status) {
        kitchenService.updateOrderDishStatus(orderDishId, status);
        return ResponseEntity.ok().build();
    }
}
