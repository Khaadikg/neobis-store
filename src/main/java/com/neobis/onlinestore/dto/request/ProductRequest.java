package com.neobis.onlinestore.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class ProductRequest {
    private String name;
    private String description;
    private Integer barcode;
    private Double price;
}
