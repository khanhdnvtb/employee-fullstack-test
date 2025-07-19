package com.employeesfullstack.employees.service;

import com.employeesfullstack.employees.dto.*;
import com.employeesfullstack.employees.dto.ShopProductDTO;
import com.employeesfullstack.employees.entity.Image;
import com.employeesfullstack.employees.entity.Product;
import com.employeesfullstack.employees.entity.Variant;
import com.employeesfullstack.employees.repository.ImageRepository;
import com.employeesfullstack.employees.repository.ProductRepository;
import com.employeesfullstack.employees.repository.VariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImportDataService {
    private final ProductRepository productRepository;
    private final VariantRepository variantRepository;
    private final ImageRepository imageRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    private static final String DATA_URL = "https://famme.no/products.json";

    @Scheduled(
            initialDelayString = "${import.initial-delay}",
            fixedDelayString = "${import.fixed-delay}"
    )
    @Transactional
    public void fetchAndSave() {
        ShopProductDTO dto = restTemplate.getForObject(DATA_URL, ShopProductDTO.class);
        if (dto == null || dto.getProducts() == null) return;
        List<ProductDTO> products = dto.getProducts();
        int limit = Math.min(products.size(), 50);
        List<ProductDTO> limited = products.subList(0, limit);

        for (ProductDTO productDTO : limited) {
            Product product = Product.builder()
                    .title(productDTO.getTitle())
                    .handle(productDTO.getHandle())
                    .vendor(productDTO.getVendor())
                    .productType(productDTO.getProductType())
                    .build();
            productRepository.save(product);

            if (productDTO.getImages() != null) {
                for (FeaturedImageDTO imgDto : productDTO.getImages()) {

                    Image img = Image.builder()
                            .product(product)
                            .src(imgDto.getSrc())
                            .build();
                    imageRepository.save(img);
                }
            } else {
                System.out.println("Product: " + productDTO.getTitle() + " has NO images");
            }

            if (productDTO.getVariants() != null) {
                for (VariantDTO variantDTO : productDTO.getVariants()) {
                    Variant variant = Variant.builder()
                            .product(product)
                            .title(variantDTO.getTitle())
                            .option1(variantDTO.getOption1())
                            .option2(variantDTO.getOption2())
                            .option3(variantDTO.getOption3())
                            .sku(variantDTO.getSku())
                            .requiresShipping(Boolean.TRUE.equals(variantDTO.getRequiresShipping()))
                            .taxable(Boolean.TRUE.equals(variantDTO.getTaxable()))
                            .available(Boolean.TRUE.equals(variantDTO.getAvailable()))
                            .price((variantDTO.getPrice() != null && !variantDTO.getPrice().isEmpty()) ? new BigDecimal(variantDTO.getPrice()) : null)
                            .compareAtPrice((variantDTO.getCompareAtPrice() != null && !variantDTO.getCompareAtPrice().isEmpty()) ? new BigDecimal(variantDTO.getCompareAtPrice()) : null)
                            .grams(variantDTO.getGrams())
                            .build();
                    variantRepository.save(variant);
                }
            }
        }
    }
}