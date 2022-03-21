package com.newBooking.Data.mapper.Booking;

import com.newBooking.Data.DTO.Room.createRoomDTO;
import com.newBooking.Data.Entity.RoomEntity;

public class RoomMapper {
    public static RoomEntity fromDtoToEntity (createRoomDTO roomEntity) {
        RoomEntity room = new RoomEntity();
        room.setId(roomEntity.getId());
        room.setName(roomEntity.getName());

        return room;
    }
}
