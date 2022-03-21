package com.newBooking.Data.DTO.User;

import com.newBooking.Data.Entity.UserEntity;
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
