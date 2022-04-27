package com.newBooking.domain.Repository;

import com.newBooking.domain.Entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    List<BookingEntity> findAll();
    List<BookingEntity> findBookingEntitiesByFromUtcIsBeforeAndToUtcIsAfter(LocalDateTime fromUtc, LocalDateTime toUtc);

}
