package com.inventorymanager.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter

@Entity
public class Product extends BaseEntity{
    @Column(unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Boolean deleted=false;

    @Column(nullable = false)
    private Boolean disabled=false;

    @Column(nullable = false)
    private int quantity;
}
