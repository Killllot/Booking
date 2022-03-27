package com.newBooking.domain.Service;

import com.newBooking.domain.Entity.RoomEntity;
import com.newBooking.domain.Exeption.BookingException;
import com.newBooking.domain.Exeption.ConfigurationException;
import com.newBooking.domain.Repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.org.hamcrest.collection.IsEmptyCollection;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;
    private RoomService roomService;
    @BeforeEach
    void setUp() {
        roomService = new RoomService(roomRepository);
    }

    @Test
    void createRoom() {
        RoomEntity room = new RoomEntity(
                1l,
                "test"
        );

        roomService.createRoom(room);
        ArgumentCaptor<RoomEntity> roomEntityArgumentCaptor = ArgumentCaptor.forClass(RoomEntity.class);

        verify(roomRepository).save(roomEntityArgumentCaptor.capture());

        RoomEntity capturedRoom = roomEntityArgumentCaptor.getValue();
        assertThat(capturedRoom).isEqualTo(room);
    }

    @Test
    void notEmptyGetUnoccupiedRooms() throws ConfigurationException, BookingException {
        LocalDateTime fromUtc = LocalDateTime.of(2022,3,27,20,0);
        LocalDateTime toUtc = LocalDateTime.of(2022,3,27,21,0);
        roomService.getUnoccupiedRooms(fromUtc,toUtc);
        var list = roomService.getUnoccupiedRooms(fromUtc,toUtc);

        assertThat(list, not(IsEmptyCollection.empty()));
    }

    @Test
    void findAllRooms() {
        roomRepository.findAll();
        verify(roomRepository).findAll();
    }
}