package com.employeesfullstack.employees.controller;

import com.employeesfullstack.employees.dto.*;
import com.employeesfullstack.employees.entity.Product;
import com.employeesfullstack.employees.service.ProductService;
import com.employeesfullstack.employees.constant.ProductType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/products")
    public String loadProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "fragments/product-table";
    }

    @GetMapping("/products/new-form")
    public String newForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("productTypes", ProductType.values());
        return "fragments/product-form";
    }

    @PostMapping("/products")
    public String create(@ModelAttribute ProductDTO productDTO, Model model, HttpServletRequest request) {
        var errors = new ArrayList<String>();
        if (productDTO.getTitle() == null || productDTO.getTitle().trim().isEmpty()) {
            errors.add("Title is required");
        }
        if (productDTO.getHandle() == null || productDTO.getHandle().trim().isEmpty()) {
            errors.add("Handle is required");
        }
        if (productDTO.getVendor() == null || productDTO.getVendor().trim().isEmpty()) {
            errors.add("Vendor is required");
        }
        if (productDTO.getProductType() == null || productDTO.getProductType().trim().isEmpty()) {
            errors.add("Product type is required");
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("product", productDTO);
            model.addAttribute("productTypes", ProductType.values());
            return "fragments/product-form";
        }

        Product product = productService.fromDTO(productDTO);
        productService.save(product);
        model.addAttribute("products", productService.findAll());
        return "fragments/product-table";
    }
}