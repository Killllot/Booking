package com.newBooking.Data.models;


import com.newBooking.domain.Entity.BookingEntity;
import com.newBooking.domain.Entity.RoomEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class Booking {

    private Long id;
    private LocalDateTime fromUtc;
    private LocalDateTime toUtc;
    private String Comment;
    List<Map<Long,String>> roomEntityList;

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
