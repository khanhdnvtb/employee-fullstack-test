package com.employeesfullstack.employees.service;

import com.employeesfullstack.employees.dto.*;
import com.employeesfullstack.employees.entity.Product;
import com.employeesfullstack.employees.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.springframework.data.domain.Sort;
import com.employeesfullstack.employees.entity.Variant;
import com.employeesfullstack.employees.entity.Image;
import com.employeesfullstack.employees.dto.ShopProductDTO;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Transactional
    public Product save(Product product) {
        if (product.getVariants() != null) {
            product.getVariants().forEach(v -> v.setProduct(product));
        }
        if (product.getImages() != null) {
            product.getImages().forEach(i -> i.setProduct(product));
        }
        return productRepository.save(product);
    }

    public Product fromDTO(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setTitle(productDTO.getTitle());
        product.setHandle(productDTO.getHandle());
        product.setVendor(productDTO.getVendor());
        product.setProductType(productDTO.getProductType());

        // Variants
        if (productDTO.getVariants() != null) {
            List<Variant> variants = new ArrayList<>();
            for (VariantDTO vdto : productDTO.getVariants()) {
                Variant v = new Variant();
                v.setId(vdto.getId());
                v.setTitle(vdto.getTitle());
                v.setOption1(vdto.getOption1());
                v.setOption2(vdto.getOption2());
                v.setOption3(vdto.getOption3());
                v.setSku(vdto.getSku());
                v.setRequiresShipping(Boolean.TRUE.equals(vdto.getRequiresShipping()));
                v.setTaxable(Boolean.TRUE.equals(vdto.getTaxable()));
                v.setAvailable(Boolean.TRUE.equals(vdto.getAvailable()));
                if (vdto.getPrice() != null && !vdto.getPrice().isEmpty()) {
                    v.setPrice(new java.math.BigDecimal(vdto.getPrice()));
                }
                if (vdto.getCompareAtPrice() != null && !vdto.getCompareAtPrice().isEmpty()) {
                    v.setCompareAtPrice(new java.math.BigDecimal(vdto.getCompareAtPrice()));
                }
                v.setGrams(vdto.getGrams());
                v.setProduct(product);
                variants.add(v);
            }
            product.setVariants(variants);
        }

        // Images
        if (productDTO.getImages() != null) {
            List<Image> images = new ArrayList<>();
            for (FeaturedImageDTO idto : productDTO.getImages()) {
                Image img = new Image();
                img.setId(idto.getId());
                img.setProduct(product);
                img.setSrc(idto.getSrc());
                images.add(img);
            }
            product.setImages(images);
        }
        return product;
    }
}
