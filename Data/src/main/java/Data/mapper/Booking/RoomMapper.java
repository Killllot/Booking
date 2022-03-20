package Data.mapper.Booking;

import Data.DTO.Room.createRoomDTO;
import Data.Entity.RoomEntity;

public class RoomMapper {
    public static RoomEntity fromDtoToEntity (createRoomDTO roomEntity) {
        RoomEntity room = new RoomEntity();
        room.setId(roomEntity.getId());
        room.setName(roomEntity.getName());

        return room;
    }
}
