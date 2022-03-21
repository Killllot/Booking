package com.newBooking.Data.mapper.Booking;

import com.newBooking.Data.DTO.Booking.createBookingDTO;
import com.newBooking.Data.Entity.BookingEntity;

public class BookingMapper {

    public static BookingEntity fromDtoToEntity(createBookingDTO dto) {
        BookingEntity booking = new BookingEntity();
        booking.setId(dto.getId());
        booking.setFromUtc(dto.getFromUtc());
        booking.setToUtc(dto.getToUtc());
        booking.setComment(dto.getComment());
        return booking;

    }
}
