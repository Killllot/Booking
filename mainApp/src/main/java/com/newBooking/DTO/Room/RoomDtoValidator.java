package com.newBooking.DTO.Room;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RoomDtoValidator {

    private Long id;
    @NotBlank(message = "Имя комнаты не должно быть пустым")
    @Size(min = 3, message = "Имя комнаты должно иметь более 3-х символов")
    private String name;

    public RoomDtoValidator(Long id, String name) {
        setId(id);
        setName(name);
    }

    public RoomDtoValidator() {

    }

    public static RoomDtoValidator toModel (RoomDtoValidator roomEntity) {
        RoomDtoValidator room = new RoomDtoValidator();
        room.setId(room.getId());
        room.setName(room.getName());

        return room;
    }

}
