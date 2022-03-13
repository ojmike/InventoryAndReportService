package com.reportmanager.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderDetailsRequest {

    private Long id;

    private List<ProductOrderRequest> productOrderResponseList;

    private BigDecimal totalPrice;

    private String status;
}
