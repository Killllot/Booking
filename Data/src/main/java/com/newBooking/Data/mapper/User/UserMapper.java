package com.newBooking.Data.mapper.User;

//import com.newBooking.DTO.User.createUserDTO;
//import com.newBooking.Data.Entity.UserEntity;

import com.newBooking.Data.models.User;
import com.newBooking.domain.Entity.UserEntity;

public class UserMapper {

    public static User toModel(UserEntity entity) {
        User model = new User();
        model.setId(entity.getId());
        model.setUserName(entity.getUsername());
        return model;
    }
}
