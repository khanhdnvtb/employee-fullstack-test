package com.employeesfullstack.employees.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String title;
    private String handle;
    private String vendor;
    @JsonProperty("product_type")
    private String productType;
    private List<VariantDTO> variants;
    @JsonProperty("images")
    private List<FeaturedImageDTO> images;
}