package com.newBooking.Data.models;


import com.newBooking.domain.Entity.RoomEntity;
import lombok.Data;

@Data
public class Room {

    private Long id;
    private String name;

    public Room(Long id, String name) {
        setId(id);
        setName(name);
    }

    public Room() {

    }

    public static Room toModel (RoomEntity roomEntity) {
        Room room = new Room();
        room.setId(roomEntity.getId());
        room.setName(roomEntity.getName());

        return room;
    }

}
