package com.newBooking.dto.room;

import com.newBooking.domain.entity.RoomEntity;

public class RoomDto {
    public static RoomEntity fromDtoToEntity (ValidatedRoomDto roomEntity) {
        RoomEntity room = new RoomEntity();
        room.setId(roomEntity.getId());
        room.setName(roomEntity.getName());

        return room;
    }
}
