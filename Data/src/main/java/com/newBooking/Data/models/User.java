package com.newBooking.Data.models;


import lombok.Data;

@Data
public class User {
    private Long id;
    private String userName;

    public User() {
    }

}
