package com.newBooking.DTO.Room;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class createRoomDtoValidator {

    private Long id;
    @NotBlank(message = "Имя комнаты не должно быть пустым")
    @Size(min = 3, message = "Имя комнаты должно иметь более 3-х символов")
    private String name;

    public createRoomDtoValidator(Long id, String name) {
        setId(id);
        setName(name);
    }

    public createRoomDtoValidator() {

    }

    public static createRoomDtoValidator toModel (createRoomDtoValidator roomEntity) {
        createRoomDtoValidator room = new createRoomDtoValidator();
        room.setId(room.getId());
        room.setName(room.getName());

        return room;
    }

}
