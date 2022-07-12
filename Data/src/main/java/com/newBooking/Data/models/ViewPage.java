package com.newBooking.Data.models;

import com.newBooking.domain.entity.BookingEntity;
import lombok.Data;

import java.util.List;

@Data
public class ViewPage {

    private Long first;
    private Long last;
    private Long count;

    private List<BookingEntity> bookings;

}
