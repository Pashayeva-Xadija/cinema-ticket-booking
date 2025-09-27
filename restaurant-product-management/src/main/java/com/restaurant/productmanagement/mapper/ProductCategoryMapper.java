package com.restaurant.productmanagement.mapper;

import com.restaurant.productmanagement.dto.ProductCategoryDto;
import com.restaurant.productmanagement.model.ProductCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {
    ProductCategoryDto toDto(ProductCategory category);

    ProductCategory toEntity(ProductCategoryDto dto);
}
