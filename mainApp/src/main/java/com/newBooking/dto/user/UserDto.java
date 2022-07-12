package com.newBooking.dto.user;

import com.newBooking.domain.entity.UserEntity;

public class UserDto {

    public static UserEntity fromDtoToEntity (ValidatedUserDto userDTO) {
        UserEntity user = new UserEntity();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUserName());

        return user;
    }

}
