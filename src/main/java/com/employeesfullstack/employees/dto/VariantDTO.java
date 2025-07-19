package com.employeesfullstack.employees.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariantDTO {
    private Long id;
    private String title;
    private String option1;
    private String option2;
    private String option3;
    private String sku;
    private Boolean requiresShipping;
    private Boolean taxable;
    @JsonProperty("featured_image")
    private FeaturedImageDTO featuredImage;
    private Boolean available;
    private String price;
    private Integer grams;
    private String compareAtPrice;
    private Long productId;
}