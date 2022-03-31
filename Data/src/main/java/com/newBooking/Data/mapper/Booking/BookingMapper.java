package com.newBooking.Data.mapper.Booking;


import com.newBooking.Data.models.Booking;
import com.newBooking.domain.Entity.BookingEntity;
import com.newBooking.domain.Entity.RoomEntity;

import java.util.List;
import java.util.stream.Collectors;

public class BookingMapper {
    public static Booking toModel(BookingEntity book) {
        Booking booking = new Booking();
        booking.setId(book.getId());
        booking.setFromUtc(book.getFromUtc());
        booking.setToUtc(book.getToUtc());
        booking.setComment(book.getComment());
        var list = book.getRoomEntityList().stream()
                .collect(Collectors.toMap(RoomEntity::getId,RoomEntity::getName));
        booking.setRoomEntityList(List.of(list));

        return booking;
    }
}
