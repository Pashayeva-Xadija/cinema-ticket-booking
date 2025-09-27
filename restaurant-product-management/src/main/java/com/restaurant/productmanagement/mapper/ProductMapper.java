package com.restaurant.productmanagement.mapper;

import com.restaurant.productmanagement.dto.ProductDto;
import com.restaurant.productmanagement.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ProductDto toDto(Product product);

    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductDto dto);
}
