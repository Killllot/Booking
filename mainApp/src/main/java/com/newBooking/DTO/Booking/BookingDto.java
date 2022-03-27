package com.newBooking.DTO.Booking;

import com.newBooking.domain.Entity.BookingEntity;
import com.newBooking.domain.Entity.RoomEntity;
import com.newBooking.domain.Entity.UserEntity;

public class BookingDto {

    public static BookingEntity fromDtoToEntity(createBookingDtoValidator dto) {
        BookingEntity booking = new BookingEntity();
        booking.setId(dto.getId());
        booking.setFromUtc(dto.getFromUtc());
        booking.setToUtc(dto.getToUtc());
        booking.setComment(dto.getComment());
        booking.setUser(new UserEntity(dto.getUserId(), null));
        booking.setRoomId(dto.getRoomId());
        return booking;

   }
}
