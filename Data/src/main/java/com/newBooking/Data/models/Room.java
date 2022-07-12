package com.newBooking.Data.models;


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

}
