package com.restaurant.productmanagement.mapper;

import com.restaurant.productmanagement.dto.CartDto;
import com.restaurant.productmanagement.dto.CartItemDto;
import com.restaurant.productmanagement.model.Cart;
import com.restaurant.productmanagement.model.CartItem;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(source = "product.id",   target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "unitPrice",    target = "unitPrice")
    @Mapping(source = "quantity",     target = "quantity")
    @Mapping(target = "lineTotal",    ignore = true)
    CartItemDto toItemDto(CartItem item);

    List<CartItemDto> toItemDtoList(List<CartItem> items);

    @Mapping(source = "items", target = "items")
    @Mapping(target = "total", ignore = true)
    CartDto toDto(Cart cart);

    @AfterMapping
    default void fillLineTotal(CartItem item, @MappingTarget CartItemDto dto) {
        dto.setLineTotal(item.getLineTotal());
    }

    @AfterMapping
    default void setTotal(Cart cart, @MappingTarget CartDto dto) {
        BigDecimal total = cart.getItems().stream()
                .map(CartItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dto.setTotal(total);
    }
}
