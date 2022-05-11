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

}
