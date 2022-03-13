package com.inventorymanager.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateProductRequest {

    @NotNull
    @Positive
    private Long id;

    @NotBlank
    @Column(name = "product_name")
    private String productName;

    @NotBlank
    @Column(name = "product_description")
    private String productDescription;

    @NotNull
    @Min(1)
    @Column(name = "product_price")
    private BigDecimal productPrice;

    @Column(name = "product_quantity")
    private int productQuantity;

    @Column(name = "disabled")
    private Boolean disabled;
}
