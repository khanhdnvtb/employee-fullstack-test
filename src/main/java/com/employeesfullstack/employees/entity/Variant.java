package com.employeesfullstack.employees.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "variant")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Variant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String title;
    private String option1;
    private String option2;
    private String option3;
    private String sku;
    private Boolean requiresShipping;
    private Boolean taxable;
    private Boolean available;
    private BigDecimal price;

    @Column(name = "compare_at_price")
    private BigDecimal compareAtPrice;
    private Integer grams;
}