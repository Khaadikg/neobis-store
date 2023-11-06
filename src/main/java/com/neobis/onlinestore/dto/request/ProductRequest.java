package com.neobis.onlinestore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class ProductRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @Positive
    private Integer barcode;
    @Positive
    private Double price;
    @NotBlank
    private String productType;
}
