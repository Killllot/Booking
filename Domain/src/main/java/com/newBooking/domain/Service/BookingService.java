package com.newBooking.domain.Service;


import com.newBooking.domain.Entity.BookingEntity;
import com.newBooking.domain.Entity.RoomEntity;
import com.newBooking.domain.Entity.UserEntity;
import com.newBooking.domain.Repository.IBookingRepository;
import com.newBooking.domain.Repository.IRoomRepository;
import com.newBooking.domain.Repository.IUserRepository;
import com.newBooking.domain.Exeption.BookingException;
import com.newBooking.domain.Exeption.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@Service
public class BookingService {

    @Value("${const.minimumBookingDuration}")
    private long minimumBookingDuration;

    @Autowired
    private IBookingRepository bookingRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoomRepository roomRepository;

    public BookingEntity createBooking(BookingEntity booking)  {

        UserEntity user = userRepository.findById(booking.getUser().getId()).orElse(null);
        if(user==null) {
            throw  new RuntimeException("Пользователя с таким id не существует");
        }

        RoomEntity room = roomRepository.findById(booking.getRoomId()).orElse(null);
        if (room==null) {
            throw new RuntimeException("Комнаты с таким id не существует");
        }

        if( ChronoUnit.MINUTES.between(booking.getFromUtc(), booking.getToUtc()) < minimumBookingDuration) {
            throw new RuntimeException("Время бронирования не может быть отрицательным и должно быть больше "+ minimumBookingDuration +" минут");
        }

        List<BookingEntity> list = bookingRepository.findAll();
        var alreadyCreatedBooking = list.stream()
                .filter(value -> value.getRoomEntityList().contains(room))
                .anyMatch(value ->
                value.getFromUtc().compareTo(booking.getFromUtc())<=0
                &&value.getToUtc().compareTo(booking.getFromUtc())>=0
                ||value.getFromUtc().compareTo(booking.getToUtc())<=0
                &&value.getToUtc().compareTo(booking.getToUtc())>=0
                ||value.getFromUtc().compareTo(booking.getFromUtc())==0
                &&value.getToUtc().compareTo(booking.getToUtc())==0
        );
        if(alreadyCreatedBooking) {
            throw new RuntimeException("Бронирование с такой датой уже существует");
        }

        List <RoomEntity> newlist = new ArrayList<>();
        newlist.add(room);
        booking.setUser(user);
        booking.setRoomEntityList(newlist);

        return bookingRepository.save(booking);
    }

    public BookingEntity getBooking(Long id) {
        BookingEntity booking = bookingRepository.findById(id).orElse(null);
        if(booking==null) {
            throw new RuntimeException("Бронирование с таким id не найдено");
        }
        return booking;
    }
}
