package com.inventorymanager.payload.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class ProductOrderResponse {

    private Long id;

    private String customerName;

    private String customerAddress;

    private Integer quantity;

    private String phoneNumber;

    private BigDecimal productPrice;

    private String productName;

    private String productDescription;

    private BigDecimal productSellingPrice;

}
