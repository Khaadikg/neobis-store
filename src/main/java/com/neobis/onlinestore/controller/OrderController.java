package com.neobis.onlinestore.controller;

import com.neobis.onlinestore.dto.request.OrderRequest;
import com.neobis.onlinestore.entity.Order;
import com.neobis.onlinestore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/orders")
@Tag(name = "Order controller", description = "Uses for logic upon orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Make order", description = "Making order using order requests")
    public ResponseEntity<String> makeOrder(@RequestBody List<OrderRequest> requests,
                                            @RequestParam String address,
                                            @RequestParam String type) {
        return orderService.makeOrder(requests, address, type);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Decline order", description = "Deletes order with order details by id")
    public ResponseEntity<String> declineOrder(@PathVariable Long id,
                                               @RequestParam(required = false) String reason) {
        return orderService.declineOrder(id, reason);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
}
