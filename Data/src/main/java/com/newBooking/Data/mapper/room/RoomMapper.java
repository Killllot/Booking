package com.newBooking.Data.mapper.room;

//import com.newBooking.DTO.Room.createRoomDTO;
//import com.newBooking.Data.Entity.RoomEntity;

import com.newBooking.Data.models.Room;
import com.newBooking.domain.entity.RoomEntity;

public class RoomMapper {
    public static Room toModel (RoomEntity roomEntity) {
        Room room = new Room();
        room.setId(roomEntity.getId());
        room.setName(roomEntity.getName());

        return room;
    }
}
