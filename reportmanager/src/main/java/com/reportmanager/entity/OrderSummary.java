package com.reportmanager.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class OrderSummary extends BaseEntity{

    private BigDecimal totalPrice;

    private String status;

    private Integer totalOrder;

}
