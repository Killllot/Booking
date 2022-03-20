package Data.DTO.Room;

import Data.Entity.RoomEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class createRoomDTO {

    private Long id;
    @NotBlank(message = "Имя комнаты не должно быть пустым")
    @Size(min = 3, message = "Имя комнаты должно иметь более 3-х символов")
    private String name;

    public createRoomDTO(Long id, String name) {
        setId(id);
        setName(name);
    }

    public createRoomDTO() {

    }

    public static createRoomDTO toModel (RoomEntity roomEntity) {
        createRoomDTO room = new createRoomDTO();
        room.setId(room.getId());
        room.setName(room.getName());

        return room;
    }

}
