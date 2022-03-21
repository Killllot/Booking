package com.newBooking.domain.Service;


import com.newBooking.Data.DTO.Booking.createBookingDTO;
import com.newBooking.Data.Entity.BookingEntity;
import com.newBooking.Data.Entity.RoomEntity;
import com.newBooking.Data.Entity.UserEntity;
import com.newBooking.Data.Repository.BookingRepository;
import com.newBooking.Data.Repository.RoomRepository;
import com.newBooking.Data.Repository.UserRepository;
import com.newBooking.Data.mapper.Booking.BookingMapper;
import com.newBooking.domain.Exeption.BookingException;
import com.newBooking.domain.Exeption.ConfigurationException;
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
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;

    public BookingEntity createBooking(createBookingDTO booking) throws BookingException, UserAlreadyExistException, ConfigurationException {
        BookingEntity saveBooking;

        UserEntity user = userRepository.findById(booking.getUserId()).orElse(null);
        if(user==null) {
            throw  new UserAlreadyExistException("Пользователя с таким id не существует");
        }

        RoomEntity room = roomRepository.findById(booking.getRoomId()).orElse(null);
        if (room==null) {
            throw new BookingException("Комнаты с таким id не существует");
        }

        if( ChronoUnit.MINUTES.between(booking.getFromUtc(), booking.getToUtc()) < minimumBookingDuration) {
            throw new BookingException("Время бронирования не может быть отрицательным и должно быть больше "+ minimumBookingDuration +" минут");
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
            throw new BookingException("Бронирование с такой датой уже существует");
        }

        List <RoomEntity> newlist = new ArrayList<>();
        newlist.add(room);
        saveBooking = BookingMapper.fromDtoToEntity(booking);
        saveBooking.setUser(user);
        saveBooking.setRoomEntityList(newlist);

        return bookingRepository.save(saveBooking);
    }

    public BookingEntity getBooking(Long id) throws BookingException {
        BookingEntity booking = bookingRepository.findById(id).orElse(null);
        if(booking==null) {
            throw new BookingException("Бронирование с таким id не найдено");
        }
        return booking;
    }
}
