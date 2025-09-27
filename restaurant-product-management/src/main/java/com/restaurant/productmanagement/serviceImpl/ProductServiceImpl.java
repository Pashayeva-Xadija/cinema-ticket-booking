package com.restaurant.productmanagement.serviceImpl;


import com.restaurant.productmanagement.dto.ProductDto;
import com.restaurant.productmanagement.mapper.ProductMapper;
import com.restaurant.productmanagement.model.Product;
import com.restaurant.productmanagement.model.ProductCategory;
import com.restaurant.productmanagement.repository.ProductCategoryRepository;
import com.restaurant.productmanagement.repository.ProductRepository;
import com.restaurant.productmanagement.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDto> getAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto create(ProductDto dto) {
        ProductCategory category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Kateqoriya tapılmadı"));

        Product product = productMapper.toEntity(dto);
        product.setCategory(category);

        Product saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }
    @Override
    public ProductDto update(Long id, ProductDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getImageUrl());

        if (dto.getCategoryId() != null) {
            ProductCategory category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        }

        Product updated = productRepository.save(product);
        return productMapper.toDto(updated);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}

