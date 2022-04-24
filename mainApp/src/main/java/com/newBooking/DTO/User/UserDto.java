package com.newBooking.DTO.User;

import com.newBooking.domain.Entity.UserEntity;

public class UserDto {

    public static UserEntity fromDtoToEntity (UserDtoValidator userDTO) {
        UserEntity user = new UserEntity();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUserName());

        return user;
    }

}
