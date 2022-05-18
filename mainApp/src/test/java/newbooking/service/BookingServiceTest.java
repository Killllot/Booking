package newbooking.service;

import com.newBooking.domain.entity.BookingEntity;
import com.newBooking.domain.entity.RoomEntity;
import com.newBooking.domain.entity.UserEntity;
import com.newBooking.domain.entity.security.ERole;
import com.newBooking.domain.entity.security.Role;
import com.newBooking.domain.repository.BookingRepository;
import com.newBooking.domain.repository.RoomRepository;
import com.newBooking.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {com.newBooking.api.BookingApp.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
@AutoConfigureMockMvc
class BookingServiceTest {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void createBooking() throws Exception{
        bookingRepository.deleteAll();
        userRepository.deleteAll();
        roomRepository.deleteAll();

        UserEntity addUser = UserEntity.builder().username("Artem").email("sasd@ad.com").password("password").roles(Set.of(new Role(ERole.ROLE_USER))).build();
        RoomEntity addRoom = RoomEntity.builder().name("Paradise").build();

        userRepository.save(addUser);
        roomRepository.save(addRoom);

        Optional<UserEntity> testUser = userRepository.findByUsername(addUser.getUsername());
        Optional<RoomEntity> testRoom = roomRepository.findByName(addRoom.getName());
        if(testUser.isPresent()&&testRoom.isPresent()) {
            BookingEntity testBook = BookingEntity.builder()
                    .Comment("This is test book")
                    .fromUtc(LocalDateTime.of(2022, 12, 2, 13, 10))
                    .toUtc(LocalDateTime.of(2022, 12, 2, 14, 10))
                    .room(testRoom.get())
                    .user(testUser.get())
                    .build();

            bookingRepository.save(testBook);

            assertNotNull(testBook);
            assertNotNull(testBook.getId());
            assertNotNull(testBook.getFromUtc());
            assertNotNull(testBook.getToUtc());
            assertNotNull(testBook.getRoom());
            assertNotNull(testBook.getUser());
            assertNotNull(testBook.getComment());

            ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/booking"));

            response.andExpect(MockMvcResultMatchers.status().isOk());
        }

    }

    @Test
    @WithMockUser
    void getBooking() throws Exception{
        bookingRepository.deleteAll();
        userRepository.deleteAll();
        roomRepository.deleteAll();

        UserEntity addUser = UserEntity.builder().username("Artem").email("sasd@ad.com").password("password").roles(Set.of(new Role(ERole.ROLE_USER))).build();
        RoomEntity addRoom = RoomEntity.builder().name("Paradise").build();

        userRepository.save(addUser);
        roomRepository.save(addRoom);

        Optional<UserEntity> testUser = userRepository.findByUsername(addUser.getUsername());
        Optional<RoomEntity> testRoom = roomRepository.findByName(addRoom.getName());
        if(testUser.isPresent()&&testRoom.isPresent()) {
            BookingEntity testBook = BookingEntity.builder()
                    .Comment("This is test book")
                    .fromUtc(LocalDateTime.of(2022, 12, 2, 13, 10))
                    .toUtc(LocalDateTime.of(2022, 12, 2, 14, 10))
                    .room(testRoom.get())
                    .user(testUser.get())
                    .build();

            bookingRepository.save(testBook);

            assertNotNull(testBook);
            assertNotNull(testBook.getId());
            assertNotNull(testBook.getFromUtc());
            assertNotNull(testBook.getToUtc());
            assertNotNull(testBook.getRoom());
            assertNotNull(testBook.getUser());
            assertNotNull(testBook.getComment());

            var cheakBook = bookingRepository.findAll();
            if(cheakBook.size()==1){
                ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/booking?bookingId=" +cheakBook.get(0).getId() ));

                response.andExpect(MockMvcResultMatchers.status().isOk());
            }
        }


    }
}