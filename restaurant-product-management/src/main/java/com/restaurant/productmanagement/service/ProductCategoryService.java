package com.restaurant.productmanagement.service;


import com.restaurant.productmanagement.dto.ProductCategoryDto;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategoryDto> getAll();
    ProductCategoryDto create(ProductCategoryDto dto);
    ProductCategoryDto update(Long id, ProductCategoryDto dto);
    void delete(Long id);
}
