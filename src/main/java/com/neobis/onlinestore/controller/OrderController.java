package com.neobis.onlinestore.controller;

import com.neobis.onlinestore.dto.request.OrderRequest;
import com.neobis.onlinestore.service.OrderService;
import com.neobis.onlinestore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> makeOrder(@RequestBody OrderRequest[] requests,
                                            @RequestParam String address,
                                            @RequestParam String type) {
        return orderService.makeOrder(requests, address, type);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> declineOrder(@PathVariable Long id,
                                               @RequestParam String reason) {
        return orderService.declineOrder(id, reason);
    }

}
