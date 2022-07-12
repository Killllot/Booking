package com.newBooking.dto.room;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ValidatedRoomDto {

    private Long id;
    @NotBlank(message = "Имя комнаты не должно быть пустым")
    @Size(min = 3, message = "Имя комнаты должно иметь более 3-х символов")
    private String name;

    public ValidatedRoomDto(Long id, String name) {
        setId(id);
        setName(name);
    }

    public ValidatedRoomDto() {

    }

    public static ValidatedRoomDto toModel (ValidatedRoomDto roomEntity) {
        ValidatedRoomDto room = new ValidatedRoomDto();
        room.setId(room.getId());
        room.setName(room.getName());

        return room;
    }

}
