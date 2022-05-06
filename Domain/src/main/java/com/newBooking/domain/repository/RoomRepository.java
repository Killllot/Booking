package com.newBooking.domain.repository;


import com.newBooking.domain.entity.BookingEntity;
import com.newBooking.domain.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<RoomEntity,Long> {
    Optional<RoomEntity> findByName(String name);
    List<RoomEntity> findAll();
    @Query("from BookingEntity book " +
            "where (book.fromUtc<=:fromTime and book.toUtc>=:fromTime OR " +
            "book.fromUtc<=:toTime and book.toUtc>=:toTime OR " +
            "book.fromUtc=:fromTime and book.toUtc=:fromTime ) and " +
            "book.room.id=:room")
    List<RoomEntity> findBookingEntityByFromUtcIsBeforeAndToUtcAfter(@Param("fromTime") LocalDateTime fromUtc, @Param("toTime") LocalDateTime toUtc, @Param("room") Long roomId);
    @Query("from BookingEntity book " +
            "where (book.fromUtc<=:fromTime and book.toUtc>=:fromTime OR " +
            "book.fromUtc<=:toTime and book.toUtc>=:toTime OR " +
            "book.fromUtc=:fromTime and book.toUtc=:fromTime ) and " +
            "book.room.id=:room")
    List<BookingEntity> newFindBookingEntityByFromUtcIsBeforeAndToUtcAfter(@Param("fromTime") LocalDateTime fromUtc,@Param("toTime") LocalDateTime toUtc,@Param("room") Long roomId);

}
