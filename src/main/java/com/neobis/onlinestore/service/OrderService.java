package com.neobis.onlinestore.service;

import com.neobis.onlinestore.dto.request.OrderRequest;
import com.neobis.onlinestore.dto.response.OrderResponse;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<String> makeOrder(List<OrderRequest> orderArray, String address, String type) {
        List<OrderDetails> details = mapArrayRequestToOrderDetailsList(orderArray);
        Double total = 0.0;
        for (OrderDetails det : details) total += det.getTotal();
        User user = userRepository.findByUsername(getAuthenticatedUsername()).orElseThrow(
                () -> new NotFoundException("Order: User not found by username = " + getAuthenticatedUsername())
        );
        Order order = Order.builder()
                .orderDetails(details)
                .address(address)
                .orderDeclined(false)
                .user(user)
                .totalOrderPrice(total)
                .type(OrderType.valueOf(type.toUpperCase()))
                .stage(OrderStage.ASSEMBLY)
                .build();
        details.forEach(x -> x.setOrder(order));
//        orderRepository.save(order);
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

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream().map(this::mapOrderToOrderResponse).toList();
    }

    public List<OrderResponse> getAllPersonalOrders() {
        return orderRepository.findAllPersonalOrders(userRepository.findByUsername(getAuthenticatedUsername()).orElseThrow(
                        () -> new NotFoundException("Order: User not found by username = " + getAuthenticatedUsername())
                ).getId())
                .stream().map(this::mapOrderToOrderResponse).toList();
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

    public OrderResponse mapOrderToOrderResponse(Order order) {
        return OrderResponse.builder()
                .address(order.getAddress())
                .orderDetails(order.getOrderDetails())
                .totalOrderPrice(order.getTotalOrderPrice())
                .stage(order.getStage())
                .type(order.getType())
                .build();
    }

    public String getAuthenticatedUsername() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new NotFoundException("Authenticated user is null");
        }
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }

}
