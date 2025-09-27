package com.example.bookingservice.mapper;


import com.example.bookingservice.dto.BookingRequest;
import com.example.bookingservice.dto.BookingResponse;
import com.example.bookingservice.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "validationCode", ignore = true)
    Booking toEntity(BookingRequest req);

    @Mapping(target = "userEmail", ignore = true)
    BookingResponse toResponse(Booking entity);
}