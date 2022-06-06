package com.newBooking.domain.entity;

import com.newBooking.domain.entity.BookingEntity;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Data
public class Page {
    private Long first;
    private Long last;
    private Long count;

    private List<BookingEntity> bookings;

    public Page(Long first, Long last) {
        this.first = first;
        this.last = last;
    }

    public Page(Long first, Long last, Long count, List<BookingEntity> bookings) {
        this.first = first;
        this.last = last;
        this.count = count;
        this.bookings = bookings;
    }
}

