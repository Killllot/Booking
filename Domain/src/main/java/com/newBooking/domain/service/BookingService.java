package com.newBooking.domain.service;


import com.newBooking.domain.entity.BookingEntity;
import com.newBooking.domain.entity.RoomEntity;
import com.newBooking.domain.entity.UserEntity;
import com.newBooking.domain.repository.BookingRepository;
import com.newBooking.domain.repository.RoomRepository;
import com.newBooking.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;


@Service
public class BookingService {

    @Value("${const.minimumBookingDuration}")
    private long minimumBookingDuration;

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;

    public BookingEntity createBooking(BookingEntity booking)  {

        UserEntity user = userRepository.findById(booking.getUser().getId()).orElse(null);
        if(user==null) {
            throw  new RuntimeException("Пользователя с таким id не существует");
        }

        RoomEntity room = roomRepository.findById(booking.getRoom().getId()).orElse(null);
        if (room==null) {
            throw new RuntimeException("Комнаты с таким id не существует");
        }

        if( ChronoUnit.MINUTES.between(booking.getFromUtc(), booking.getToUtc()) < minimumBookingDuration) {
            throw new RuntimeException("Время бронирования не может быть отрицательным и должно быть больше "+ minimumBookingDuration +" минут");
        }

        var newAlreadyCreatedBooking = bookingRepository.findBookingEntityByFromUtcIsBeforeAndToUtcAfter(booking.getFromUtc(),booking.getToUtc(),booking.getRoom().getId());
        if(newAlreadyCreatedBooking.size()!=0) {
            throw new RuntimeException("Бронирование с такой датой уже существует");
        }

        booking.setUser(user);
        booking.setRoom(room);

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