package com.newBooking.Data.models;


import com.newBooking.domain.Entity.UserEntity;
import lombok.Data;

@Data
public class User {
    private Long id;
    private String userName;

    public User() {
    }

}
