package com.neobis.onlinestore.service;

import com.neobis.onlinestore.dto.request.OrderRequest;
import com.neobis.onlinestore.entity.Order;
import com.neobis.onlinestore.entity.OrderDetails;
import com.neobis.onlinestore.entity.Product;
import com.neobis.onlinestore.entity.User;
import com.neobis.onlinestore.entity.enums.OrderStage;
import com.neobis.onlinestore.entity.enums.OrderType;
import com.neobis.onlinestore.exception.NotFoundException;
import com.neobis.onlinestore.repository.OrderRepository;
import com.neobis.onlinestore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final UserRepository userRepository;

    public ResponseEntity<String> makeOrder(List<OrderRequest> orderArray, String address, String type) {
        List<OrderDetails> details = mapArrayRequestToOrderDetailsList(orderArray);
        Double total = 0.0;
        for(OrderDetails det : details) total += det.getTotal();
        Order order = Order.builder()
                .orderDetails(details)
                .address(address)
                .orderDeclined(false)
                .totalOrderPrice(total)
                .type(OrderType.valueOf(type.toUpperCase()))
                .stage(OrderStage.ASSEMBLY)
                .build();
        details.forEach(x -> x.setOrder(order));
        orderRepository.save(order);
        return ResponseEntity.ok("Order has been accepted");
    }

    public ResponseEntity<String> declineOrder(Long id, String reason) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No such order found by id = " + id)
        );
        if (!order.getStage().equals(OrderStage.ASSEMBLY)) {
            return ResponseEntity.badRequest().body("Order cant be declined after assembly");
        }
        order.setOrderDeclined(true);
        orderRepository.save(order);
        return ResponseEntity.ok("Order has been declined \n" + "Reason: " + reason);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getAllPersonalOrders() {
        return orderRepository.findAllPersonalOrders(getAuthenticatedUser().getId());
    }

    private List<OrderDetails> mapArrayRequestToOrderDetailsList(List<OrderRequest> array) {
        List<OrderDetails> list = new ArrayList<>();
        for (OrderRequest request : array) {
            Product product = productService.checkExistAndReturnProductByBarcode(request.getBarcode());
            list.add(OrderDetails.builder()
                    .product(product)
                    .total(request.getAmount() * product.getPrice())
                    .amount(request.getAmount())
                    .build());
        }
        return list;
    }

    public User getAuthenticatedUser() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new NotFoundException("Authenticated user is null");
        }
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
