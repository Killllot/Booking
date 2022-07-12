package com.newBooking.dto.user;

import com.newBooking.domain.entity.UserEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ValidatedUserDto {

    private Long id;
    @NotNull
    @Size(min = 3, message = "Имя пользователя должно быть более 3-x символов")
    private String userName;
    private String email;
    @NotNull
    private String password;

    public static ValidatedUserDto toModel(UserEntity entity) {
        ValidatedUserDto model = new ValidatedUserDto();
        model.setId(entity.getId());
        model.setUserName(entity.getUsername());
        return model;
    }

    public ValidatedUserDto() {
    }
}
