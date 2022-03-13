package com.inventorymanager.entity;

import com.inventorymanager.payload.enums.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Entity
@Table(name="product_order")
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrder extends BaseEntity{

    private String customerName;

    private String productName;

    private String productDescription;

    private String address;

    private Integer quantity;

    private BigDecimal sellingPrice;

    private BigDecimal productPrice;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
