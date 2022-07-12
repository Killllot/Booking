package newbooking.service;

import com.newBooking.domain.entity.RoomEntity;
import com.newBooking.domain.repository.RoomRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application.yaml")
@SpringBootTest(classes = {com.newBooking.api.BookingApp.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {RoomServiceTest.Initializer.class})
class RoomServiceTest {

    @Value("${const.minimumBookingDuration}")
    String minimumBookingDuration;

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
    private RoomRepository roomRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    private void ending() {
        roomRepository.deleteAll();
    }

    @Test
    @WithMockUser
    void whenValidInput_createRoom_return200() throws Exception {
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/room/create")
                .contentType(APPLICATION_JSON)
                .content("{\"name\":\"Завтрак\"}"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(jsonPath("$.size()", CoreMatchers.is(2)));

    }

    @Test
    @WithMockUser
    void incorrectBody_in_CreateRoom_return400() throws Exception {
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/room/create")
                .contentType(APPLICATION_JSON)
                .content("{\"null\":\"Завтрак\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    @WithMockUser
    void exception_in_createRoom_return500() throws Exception {
        ResultActions firstResponse = mockMvc.perform(MockMvcRequestBuilders.post("/api/room/create")
                        .contentType(APPLICATION_JSON)
                        .content("{\"name\":\"Завтрак\"}"))
                .andDo(print())
                .andExpect(status().isOk());

        try {
            ResultActions secondResponse = mockMvc.perform(MockMvcRequestBuilders.post("/api/room/create")
                            .contentType(APPLICATION_JSON)
                            .content("{\"name\":\"Завтрак\"}"))
                    .andDo(print())
                    .andExpect(status().isInternalServerError());
        } catch (NestedServletException e) {
            Exception exception = assertThrows(RuntimeException.class , () -> {
                throw e.getCause();
            });
            assertThat(exception.getMessage())
                    .containsPattern("Комната с таким названием уже есть");
        }

    }

    @Test
    @WithMockUser
    void getUnoccupiedRooms_return200() throws Exception {
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/room/getUnoccupiedRooms")
                        .param("FromUtc","2022-02-21 12:01")
                        .param("ToUtc","2022-02-21 13:01"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser
    void exception_in_getUnoccupiedRooms_return500 () throws Exception {

        try {
            ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/room/getUnoccupiedRooms")
                            .param("FromUtc","2022-02-21 12:01")
                            .param("ToUtc","2022-02-21 12:01"))
                    .andDo(print())
                    .andExpect(status().isInternalServerError());
        } catch (NestedServletException e) {
            Exception exception = assertThrows(RuntimeException.class , () -> {
                throw e.getCause();
            });
            assertThat(exception.getMessage())
                    .containsPattern("Время бронирования не может быть отрицательным и должно быть больше "+ minimumBookingDuration +" минут");
        }

    }

    @Test
    @WithMockUser
    void getAll_return200 () throws Exception {

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/room/getAll"))
                .andDo(print())
                .andExpect(status().isOk());

    }
}