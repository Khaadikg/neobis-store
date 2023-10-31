package com.neobis.onlinestore.dto.response;

import lombok.*;

@Getter @Setter
@Builder
public class ProductResponse {
    private String name;
    private String description;
    private Integer barcode;
    private Double price;
}
