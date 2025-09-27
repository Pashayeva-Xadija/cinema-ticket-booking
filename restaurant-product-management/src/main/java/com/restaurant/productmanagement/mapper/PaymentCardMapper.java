package com.restaurant.productmanagement.mapper;


import com.restaurant.productmanagement.dto.PaymentCardDto;
import com.restaurant.productmanagement.model.PaymentCard;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentCardMapper {
    PaymentCardDto toDto(PaymentCard card);
}
