package com.neobis.onlinestore.dto.response;

import com.neobis.onlinestore.entity.ProductType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class ProductResponse {
    private String name;
    private String description;
    private Integer barcode;
    private Double price;
    private ProductType productType;
}
