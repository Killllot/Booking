package newbooking.service;

import com.newBooking.domain.entity.BookingEntity;
import com.newBooking.domain.entity.RoomEntity;
import com.newBooking.domain.entity.UserEntity;
import com.newBooking.domain.entity.security.ERole;
import com.newBooking.domain.entity.security.Role;
import com.newBooking.domain.repository.BookingRepository;
import com.newBooking.domain.repository.RoomRepository;
import com.newBooking.domain.repository.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {com.newBooking.api.BookingApp.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {BookingServiceTest.Initializer.class})
class BookingServiceTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14")
            .withDatabaseName("mydb")
            .withUsername("postgres")
            .withPassword("Kirill");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword(),
                    "spring.liquibase.enabled=true"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    private void preparation() {
        bookingRepository.deleteAll();
        userRepository.deleteAll();
        roomRepository.deleteAll();

        UserEntity addUser = UserEntity.builder().username("Artem").email("sasd@ad.com").password(encoder.encode("password")).roles(Set.of(new Role(ERole.ROLE_USER))).build();
        RoomEntity addRoom = RoomEntity.builder().name("Paradise").build();

        userRepository.save(addUser);
        roomRepository.save(addRoom);
    }


    @AfterEach
    private void ending() {
        bookingRepository.deleteAll();
        userRepository.deleteAll();
        roomRepository.deleteAll();

    }

    @Test
    @WithMockUser
    void correct_fields_createBooking() throws Exception{
        UserEntity addUser = userRepository.findAll().get(0);
        RoomEntity addRoom = roomRepository.findAll().get(0);

        Optional<UserEntity> testUser = userRepository.findByUsername(addUser.getUsername());
        Optional<RoomEntity> testRoom = roomRepository.findByName(addRoom.getName());
        if(testUser.isPresent()&&testRoom.isPresent()) {
            BookingEntity saveBooking = BookingEntity.builder()
                    .Comment("This is test book")
                    .fromUtc(LocalDateTime.of(2022, 12, 2, 13, 10))
                    .toUtc(LocalDateTime.of(2022, 12, 2, 14, 10))
                    .room(testRoom.get())
                    .user(testUser.get())
                    .build();

            BookingEntity testBook = bookingRepository.save(saveBooking);

            assertNotNull(testBook);
            assertNotNull(testBook.getId());
            assertNotNull(testBook.getFromUtc());
            assertNotNull(testBook.getToUtc());
            assertNotNull(testBook.getRoom());
            assertNotNull(testBook.getUser());
            assertNotNull(testBook.getComment());

        }

    }

    @Test
    @WithMockUser
    void getBooking_return200() throws Exception{

        UserEntity addUser = userRepository.findAll().get(0);
        RoomEntity addRoom = roomRepository.findAll().get(0);

        Optional<UserEntity> testUser = userRepository.findByUsername(addUser.getUsername());
        Optional<RoomEntity> testRoom = roomRepository.findByName(addRoom.getName());
        if(testUser.isPresent()&&testRoom.isPresent()) {
            BookingEntity saveBooking = BookingEntity.builder()
                    .Comment("This is test book")
                    .fromUtc(LocalDateTime.of(2022, 12, 2, 13, 10))
                    .toUtc(LocalDateTime.of(2022, 12, 2, 14, 10))
                    .room(testRoom.get())
                    .user(testUser.get())
                    .build();

            BookingEntity testBook = bookingRepository.save(saveBooking);

            ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/booking/get")
                            .param("bookingId", testBook.getId().toString()))
                    .andDo(print())
                    .andExpect(status().isFound());

        }
    }

    @Test
    @WithMockUser
    void incorrectParam_in_getBooking_return400() throws Exception{

        UserEntity addUser = userRepository.findAll().get(0);
        RoomEntity addRoom = roomRepository.findAll().get(0);

        Optional<UserEntity> testUser = userRepository.findByUsername(addUser.getUsername());
        Optional<RoomEntity> testRoom = roomRepository.findByName(addRoom.getName());
        if(testUser.isPresent()&&testRoom.isPresent()) {
            BookingEntity saveBooking = BookingEntity.builder()
                    .Comment("This is test book")
                    .fromUtc(LocalDateTime.of(2022, 12, 2, 13, 10))
                    .toUtc(LocalDateTime.of(2022, 12, 2, 14, 10))
                    .room(testRoom.get())
                    .user(testUser.get())
                    .build();

            BookingEntity testBook = bookingRepository.save(saveBooking);

            ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/booking/get")
                            .param("Id", testBook.getId().toString()))
                    .andDo(print())
                    .andExpect(status().isBadRequest());

        }
    }

    @Test
    @WithMockUser
    void exception_in_getBooking_return500() throws Exception{

        UserEntity addUser = userRepository.findAll().get(0);
        RoomEntity addRoom = roomRepository.findAll().get(0);

        Optional<UserEntity> testUser = userRepository.findByUsername(addUser.getUsername());
        Optional<RoomEntity> testRoom = roomRepository.findByName(addRoom.getName());
        if(testUser.isPresent()&&testRoom.isPresent()) {
            BookingEntity saveBooking = BookingEntity.builder()
                    .Comment("This is test book")
                    .fromUtc(LocalDateTime.of(2022, 12, 2, 13, 10))
                    .toUtc(LocalDateTime.of(2022, 12, 2, 14, 10))
                    .room(testRoom.get())
                    .user(testUser.get())
                    .build();

            BookingEntity testBook = bookingRepository.save(saveBooking);

            long param = testBook.getId()+ 10000L;

            try {
                ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/booking/get")
                                .param("bookingId", Long.toString(param)))
                        .andDo(print())
                        .andExpect(status().isInternalServerError());

            } catch (NestedServletException e) {
                Exception exception = assertThrows(RuntimeException.class , () -> {
                    throw e.getCause();
                });
                assertThat(exception.getMessage())
                        .containsPattern("Бронирование с таким id не найдено");
            }
        }
    }

    @Test
    @WithMockUser
    void exception_in_createBooking_return500() throws Exception{

        UserEntity addUser = userRepository.findAll().get(0);
        RoomEntity addRoom = roomRepository.findAll().get(0);

        Optional<UserEntity> testUser = userRepository.findByUsername(addUser.getUsername());
        Optional<RoomEntity> testRoom = roomRepository.findByName(addRoom.getName());
        if(testUser.isPresent()&&testRoom.isPresent()) {
            String room_id = testRoom.get().getId().toString();
            String user_id = testUser.get().getId().toString();

            ResultActions firstResponse = mockMvc.perform(MockMvcRequestBuilders.post("/api/booking/create")
                            .contentType(APPLICATION_JSON)
                            .content("{\"fromUtc\":\"2022-02-26 13:01\", " +
                                    " \"toUtc\":\"2022-02-26 14:01\", " +
                                    " \"comment\":\"testComment\", " +
                                    " \"userId\":\""+ user_id + "\"," +
                                    " \"roomId\":\""+ room_id + "\" }"))
                    .andDo(print())
                    .andExpect(status().isOk());

            try {
                ResultActions secondResponse = mockMvc.perform(MockMvcRequestBuilders.post("/api/booking/create")
                                .contentType(APPLICATION_JSON)
                                .content("{\"fromUtc\":\"2022-02-26 13:01\", " +
                                        " \"toUtc\":\"2022-02-26 14:01\", " +
                                        " \"comment\":\"testComment\", " +
                                        " \"userId\":\""+ user_id + "\"," +
                                        " \"roomId\":\""+ room_id + "\" }"))
                        .andDo(print())
                        .andExpect(status().isInternalServerError());

            } catch (NestedServletException e) {
                Exception exception = assertThrows(RuntimeException.class , () -> {
                    throw e.getCause();
                });
                assertThat(exception.getMessage())
                        .containsPattern("Бронирование с такой датой уже существует");
            }

        }
    }

    @Test
    @WithMockUser
    void incorrectParam_in_createBooking_return400() throws Exception{

        UserEntity addUser = userRepository.findAll().get(0);
        RoomEntity addRoom = roomRepository.findAll().get(0);

        Optional<UserEntity> testUser = userRepository.findByUsername(addUser.getUsername());
        Optional<RoomEntity> testRoom = roomRepository.findByName(addRoom.getName());
        if(testUser.isPresent()&&testRoom.isPresent()) {

            String room_id = testRoom.get().getId().toString();
            String user_id = testUser.get().getId().toString();

            ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/booking/create")
                            .contentType(APPLICATION_JSON)
                            .content("{\"Utc\":\"2022-02-26 13:01\", " +
                                        " \"toUtc\":\"2022-02-26 14:01\", " +
                                        " \"comment\":\"testComment\", " +
                                        " \"userId\":\""+ user_id + "\"," +
                                        " \"roomId\":\""+ room_id + "\" }"))
                    .andDo(print())
                    .andExpect(status().isBadRequest());

        }
    }

    @Test
    @WithMockUser
    void getAll_return200() throws Exception{
        var bookings = bookingRepository.findAll();

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/booking/getAll"))
                .andDo(print())
                .andExpect(status().isFound());

        response.andExpect(jsonPath("$.size()", CoreMatchers.is(bookings.size())));
    }
}