package com.neobis.onlinestore.dto.request;

import antlr.collections.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class OrderRequest {
    private Integer barcode;
    private Integer amount;
}
