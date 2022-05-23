package com.newBooking.domain.service;


import com.newBooking.domain.entity.RoomEntity;
import com.newBooking.domain.repository.BookingRepository;
import com.newBooking.domain.repository.RoomRepository;
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

    public List<RoomEntity> getUnoccupiedRooms (LocalDateTime FromUtc, LocalDateTime ToUtc) {
        if( ChronoUnit.MINUTES.between(FromUtc, ToUtc) <0) {
            throw new RuntimeException("Время бронирования не может быть отрицательным и должно быть больше "+ minimumBookingDuration +" минут");
        }
        List<RoomEntity> list = roomRepository.findAll().stream()
                .filter(value -> value.getBookings().stream().noneMatch(data ->
                        data.getFromUtc().compareTo(FromUtc) <= 0
                                && data.getToUtc().compareTo(FromUtc) >= 0
                                || data.getFromUtc().compareTo(ToUtc) <= 0
                                && data.getToUtc().compareTo(ToUtc) >= 0
                                || data.getFromUtc().compareTo(FromUtc) == 0
                                && data.getToUtc().compareTo(ToUtc) == 0))
                .collect(Collectors.toList());
        return list;
    }
}
