package com.newBooking.DTO.User;

import com.newBooking.domain.Entity.UserEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDtoValidator {

    private Long id;
    @NotNull
    @Size(min = 3, message = "Имя пользователя должно быть более 3-x символов")
    private String userName;
    private String email;
    @NotNull
    private String password;

    public static UserDtoValidator toModel(UserEntity entity) {
        UserDtoValidator model = new UserDtoValidator();
        model.setId(entity.getId());
        model.setUserName(entity.getUsername());
        return model;
    }

    public UserDtoValidator() {
    }
}
