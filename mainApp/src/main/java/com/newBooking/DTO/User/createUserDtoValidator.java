package com.newBooking.DTO.User;

import com.newBooking.domain.Entity.UserEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class createUserDtoValidator {

    private Long id;
    @NotNull
    @Size(min = 3, message = "Имя пользователя должно быть более 3-x символов")
    private String userName;

    public static createUserDtoValidator toModel(UserEntity entity) {
        createUserDtoValidator model = new createUserDtoValidator();
        model.setId(entity.getId());
        model.setUserName(entity.getUserName());
        return model;
    }

    public createUserDtoValidator() {
    }
}
