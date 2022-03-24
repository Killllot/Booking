package com.newBooking.Data.models;


import com.newBooking.domain.Entity.UserEntity;
import lombok.Data;

@Data
public class User {
    private Long id;
    private String userName;

    public static User toModel(UserEntity entity) {
        User model = new User();
        model.setId(entity.getId());
        model.setUserName(entity.getUserName());
        return model;
    }

    public User() {
    }

}
