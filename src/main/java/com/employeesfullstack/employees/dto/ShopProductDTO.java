package com.employeesfullstack.employees.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopProductDTO {
    private List<ProductDTO> products;
}