package com.neobis.onlinestore.dto.response;

import com.neobis.onlinestore.entity.OrderDetails;
import com.neobis.onlinestore.entity.enums.OrderStage;
import com.neobis.onlinestore.entity.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private OrderType type;

    private OrderStage stage;

    private String address;

    private Double totalOrderPrice;
    private List<OrderDetails> orderDetails;
}
