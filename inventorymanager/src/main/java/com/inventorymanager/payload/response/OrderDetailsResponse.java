package com.inventorymanager.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderDetailsResponse {

    private Long id;

    private List<ProductOrderResponse> productOrderResponseList;

    private BigDecimal totalPrice;

    private String status;
}
