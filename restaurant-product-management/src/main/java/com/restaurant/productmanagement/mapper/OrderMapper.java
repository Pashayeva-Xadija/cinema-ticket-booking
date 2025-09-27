package com.restaurant.productmanagement.mapper;

import com.restaurant.productmanagement.dto.*;
import com.restaurant.productmanagement.model.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "items", expression = "java(order.getItems().stream().map(i -> { " +
            "com.restaurant.productmanagement.dto.CartItemDto d = new com.restaurant.productmanagement.dto.CartItemDto();" +
            "d.setProductId(i.getProductId()); d.setProductName(i.getProductName());" +
            "d.setUnitPrice(i.getUnitPrice()); d.setQuantity(i.getQuantity()); d.setLineTotal(i.getLineTotal()); return d; }).toList())")
    OrderDto toDto(CustomerOrder order);
}
