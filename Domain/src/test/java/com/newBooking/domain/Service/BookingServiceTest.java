package com.newBooking.domain.Service;

import com.newBooking.domain.Entity.BookingEntity;
import com.newBooking.domain.Entity.RoomEntity;
import com.newBooking.domain.Entity.UserEntity;
import com.newBooking.domain.Exeption.BookingException;
import com.newBooking.domain.Exeption.UserAlreadyExistException;
import com.newBooking.domain.Repository.BookingRepository;
import com.newBooking.domain.Repository.RoomRepository;
import com.newBooking.domain.Repository.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
    private BookingService bookingService;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(new UserEntity(1l,"Ivan"));
        bookingService = new BookingService(roomRepository,bookingRepository,userRepository);
    }


    @Test
    void createBooking() throws UserAlreadyExistException, BookingException {
        BookingEntity booking = new BookingEntity();
        booking.setId(1l);
        booking.setComment("Test");
        booking.setUser(new UserEntity(1l,null));
        booking.setRoomId(2l);
        booking.setFromUtc(LocalDateTime.of(2022,3,27,20,2));
        booking.setToUtc(LocalDateTime.of(2022,3,27,21,2));

        lenient().when(bookingRepository.save(booking)).thenReturn(booking);

    }

    @Test
    void getBooking() {
        BookingEntity booking = new BookingEntity();
        booking.setId(1l);
        booking.setComment("Test");
        booking.setUser(new UserEntity(1l,null));
        booking.setRoomId(2l);
        booking.setFromUtc(LocalDateTime.of(2022,3,27,20,2));
        booking.setToUtc(LocalDateTime.of(2022,3,27,21,2));

        lenient().when(bookingRepository.findById(1l)).thenReturn(Optional.of(booking));
    }

    @Test
    void getRoom() {
        RoomEntity room = new RoomEntity(
                1l,
                "test"
        );
        lenient().when(roomRepository.findById(1l)).thenReturn(Optional.of(room));
    }
}