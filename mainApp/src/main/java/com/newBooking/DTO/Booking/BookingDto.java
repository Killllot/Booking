package com.newBooking.DTO.Booking;

import com.newBooking.domain.Entity.BookingEntity;

public class BookingDto {

    public static BookingEntity fromDtoToEntity(createBookingDtoValidator dto) {
        BookingEntity booking = new BookingEntity();
        booking.setId(dto.getId());
        booking.setFromUtc(dto.getFromUtc());
        booking.setToUtc(dto.getToUtc());
        booking.setComment(dto.getComment());
        return booking;

   }
}
