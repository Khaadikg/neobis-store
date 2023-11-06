package com.neobis.onlinestore.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class OrderRequest {
    @Positive
    private Integer barcode;
    @Positive
    private Integer amount;
}
