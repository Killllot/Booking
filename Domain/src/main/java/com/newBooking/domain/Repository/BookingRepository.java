package com.newBooking.domain.Repository;

import com.newBooking.domain.Entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    List<BookingEntity> findAll();
    @Query("from BookingEntity book " +
            "where (book.fromUtc<=:fromTime and book.toUtc>=:fromTime OR " +
            "book.fromUtc<=:toTime and book.toUtc>=:toTime OR " +
            "book.fromUtc=:fromTime and book.toUtc=:fromTime ) and " +
            "book.room.id=:room")
    List<BookingEntity> findBookingEntityByFromUtcIsBeforeAndToUtcAfter(@Param("fromTime") LocalDateTime fromUtc,@Param("toTime") LocalDateTime toUtc,@Param("room") Long roomId);

}
