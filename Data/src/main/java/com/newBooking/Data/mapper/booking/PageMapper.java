package com.newBooking.Data.mapper.booking;

import com.newBooking.Data.models.Booking;
import com.newBooking.Data.models.ViewPage;
import com.newBooking.domain.entity.Page;

import java.util.HashMap;

public class PageMapper {
    public static ViewPage toModel(Page page) {
        ViewPage newPage = new ViewPage();

        newPage.setFirst(page.getFirst());
        newPage.setLast(page.getLast());
        newPage.setCount(page.getCount());
        newPage.setBookings(page.getBookings());

        return newPage;
    }
}
