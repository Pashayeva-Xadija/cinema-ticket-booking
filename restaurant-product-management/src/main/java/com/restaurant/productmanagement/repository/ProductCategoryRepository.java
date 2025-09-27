package com.restaurant.productmanagement.repository;

import com.restaurant.productmanagement.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}

