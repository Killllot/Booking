package com.newBooking.Data.mapper.Booking;

import com.newBooking.Data.DTO.User.createUserDTO;
import com.newBooking.Data.Entity.UserEntity;

public class UserMapper {
    public static UserEntity fromDtoToEntity (createUserDTO userDTO) {
        UserEntity user = new UserEntity();
        user.setId(userDTO.getId());
        user.setUserName(userDTO.getUserName());

        return user;
    }
}
