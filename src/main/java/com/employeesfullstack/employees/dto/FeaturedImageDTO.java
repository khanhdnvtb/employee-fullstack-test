package com.employeesfullstack.employees.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeaturedImageDTO {
    private Long id;
    private Long productId;
    private String src;
    private List<Long> variantIds;
}