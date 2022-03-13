package com.inventorymanager.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateOrderRequest {

    @NotNull
    private Long productId;

    @NotEmpty
    private String customerName;

    @NotBlank
    private String address;

    @NotNull
    private Integer quantity;

    @NotBlank
    private String phoneNumber;

    @NotNull
    private BigDecimal productPrice;


}
