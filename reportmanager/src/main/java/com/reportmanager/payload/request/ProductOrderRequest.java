package com.reportmanager.payload.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductOrderRequest {
    private Long id;

    private String customerName;

    private String customerAddress;

    private Integer quantity;

    private String phoneNumber;

    private BigDecimal productPrice;

    private String productName;

    private String productDescription;

    private BigDecimal productSellingPrice;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
