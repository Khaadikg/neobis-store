package com.neobis.onlinestore.controller;

import com.neobis.onlinestore.dto.request.OrderRequest;
import com.neobis.onlinestore.dto.response.OrderResponse;
import com.neobis.onlinestore.entity.Order;
import com.neobis.onlinestore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/orders")
@Tag(name = "Order controller", description = "Uses for logic upon orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Make order", description = "Making order using order requests")
    public ResponseEntity<String> makeOrder(@Valid @RequestBody List<OrderRequest> requests,
                                            @RequestParam String address,
                                            @RequestParam String type) {
        return orderService.makeOrder(requests, address, type);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Decline order", description = "Deletes order with order details by id")
    public ResponseEntity<String> declineOrder(@PathVariable Long id,
                                               @RequestParam(required = false) String reason) {
        return orderService.declineOrder(id, reason);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get all orders", description = "Uses by admin to get all orders")
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/my-orders")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get personal orders", description = "Getting not declined authorised user's orders")
    public List<OrderResponse> getAllPersonalOrders() {
        return orderService.getAllPersonalOrders();
    }

}
