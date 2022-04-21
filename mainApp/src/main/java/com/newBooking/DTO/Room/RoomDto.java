package com.newBooking.DTO.Room;

import com.newBooking.domain.Entity.RoomEntity;

public class RoomDto {
    public static RoomEntity fromDtoToEntity (RoomDtoValidator roomEntity) {
        RoomEntity room = new RoomEntity();
        room.setId(roomEntity.getId());
        room.setName(roomEntity.getName());

        return room;
    }
}
