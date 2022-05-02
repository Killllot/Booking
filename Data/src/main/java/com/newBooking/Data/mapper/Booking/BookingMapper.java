package com.newBooking.Data.mapper.Booking;


import com.newBooking.Data.models.Booking;
import com.newBooking.domain.Entity.BookingEntity;
import com.newBooking.domain.Entity.RoomEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookingMapper {
    public static Booking toModel(BookingEntity book) {
        Booking booking = new Booking();
        booking.setId(book.getId());
        booking.setFromUtc(book.getFromUtc());
        booking.setToUtc(book.getToUtc());
        booking.setComment(book.getComment());
        var room = new HashMap<Long,String>();
        room.put(book.getRoom().getId(), book.getRoom().getName());
        booking.setRooms(room);

        return booking;
    }
}
