package com.newBooking.dto.booking;

import com.newBooking.domain.entity.BookingEntity;
import com.newBooking.domain.entity.RoomEntity;
import com.newBooking.domain.entity.UserEntity;

public class BookingDto {

    public static BookingEntity fromDtoToEntity(ValidatedBookingDto dto) {
        BookingEntity booking = new BookingEntity();
        booking.setId(dto.getId());
        booking.setFromUtc(dto.getFromUtc());
        booking.setToUtc(dto.getToUtc());
        booking.setComment(dto.getComment());
        booking.setUser(new UserEntity(dto.getUserId(), null));
        booking.setRoom(new RoomEntity(dto.getRoomId(),null));
        return booking;

   }
}
