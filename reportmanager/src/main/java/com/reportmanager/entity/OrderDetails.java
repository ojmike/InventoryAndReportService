package com.reportmanager.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class OrderDetails extends BaseEntity{

    private String customerName;

    private String customerAddress;

    private Integer quantity;

    private String phoneNumber;

    private BigDecimal productPrice;

    private String productName;

    private String productDescription;

    private BigDecimal productSellingPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderSummary orderSummary;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
