package com.newBooking.domain.Repository;

import com.newBooking.domain.Entity.BookingEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IBookingRepository extends CrudRepository<BookingEntity, Long> {
    List<BookingEntity> findAll();
}
