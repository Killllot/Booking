package domain.Service;


import Data.DTO.Room.Room;
import Data.Entity.RoomEntity;
import Data.Repository.BookingRepository;
import Data.Repository.RoomRepository;
import domain.Exeption.BookingException;
import domain.Exeption.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RoomService {

    @Value("${const.minimumBookingDuration}")
    private long minimumBookingDuration;

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookingRepository bookingRepository;

    public RoomEntity createRoom(RoomEntity room) {
        if(roomRepository.findByName(room.getName()).orElse(null)!=null){
            throw new RuntimeException("Комната с таким названием уже есть");
        }
        return roomRepository.save(room);
    }

    public List<Room> getUnoccupiedRooms (LocalDateTime FromUtc, LocalDateTime ToUtc) throws BookingException, ConfigurationException {
        if( ChronoUnit.MINUTES.between(FromUtc, ToUtc) <= minimumBookingDuration) {
            throw new BookingException("Время бронирования не может быть отрицательным и должно быть больше "+ minimumBookingDuration +" минут");
        }
        List<Room> list = roomRepository.findAll().stream()
                .filter(value -> value.getBookingEntityList().stream().noneMatch(data ->
                        data.getFromUtc().compareTo(FromUtc) <= 0
                                && data.getToUtc().compareTo(FromUtc) >= 0
                                || data.getFromUtc().compareTo(ToUtc) <= 0
                                && data.getToUtc().compareTo(ToUtc) >= 0
                                || data.getFromUtc().compareTo(FromUtc) == 0
                                && data.getToUtc().compareTo(ToUtc) == 0))
                .map(value -> new Room(value.getId(),value.getName()))
                .collect(Collectors.toList());
        return list;
    }
}
