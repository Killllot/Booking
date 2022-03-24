package com.newBooking.domain.Repository;


import com.newBooking.domain.Entity.RoomEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends CrudRepository<RoomEntity,Long> {
    Optional<RoomEntity> findByName(String name);
    List<RoomEntity> findAll();
}
