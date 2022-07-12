package newbooking.service;

import com.newBooking.controllers.BookingController;
import com.newBooking.controllers.RoomController;
import com.newBooking.controllers.UserController;
import com.newBooking.domain.entity.RoomEntity;
import com.newBooking.domain.entity.UserEntity;
import com.newBooking.domain.entity.security.ERole;
import com.newBooking.domain.entity.security.Role;
import com.newBooking.domain.repository.RoleRepository;
import com.newBooking.domain.repository.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest(classes = {com.newBooking.api.BookingApp.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {UserServiceImpTest.Initializer.class})
class UserServiceImpTest {

    @Container
    public static PostgreSQLContainer<?> postgresSQLContainer = new PostgreSQLContainer<>("postgres:14")
            .withDatabaseName("mydb")
            .withUsername("postgres")
            .withPassword("Kirill");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgresSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgresSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgresSQLContainer.getPassword(),
                    "spring.liquibase.enabled=true"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    private void preparation() {
        userRepository.deleteAll();

        List<UserEntity> users = List.of(
                UserEntity.builder().username("Artem").email("sasd@ad.com").password(encoder.encode("password")).roles(Set.of(new Role(ERole.ROLE_USER))).build(),
                UserEntity.builder().username("Andrea").email("adwwq@ad.com").password(encoder.encode("password")).roles(Set.of(new Role(ERole.ROLE_ADMIN))).build(),
                UserEntity.builder().username("Adolf").email("assas@ad.com").password(encoder.encode("password")).roles(Set.of(new Role(ERole.ROLE_ADMIN))).build());

        userRepository.saveAll(users);
    }

    @AfterEach
    private void ending() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser
    void getAll_retuen200() throws Exception{
        var users = userRepository.findAll();
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/users/getAll"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(jsonPath("$.size()", CoreMatchers.is(users.size())));

    }

    @Test
    @WithMockUser
    void getUser_return200() throws Exception{
        var user = userRepository.findByUsername("Adolf").orElse(null);
        if(user!=null) {
            ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                    .param("id", user.getId().toString()));

            response.andExpect(MockMvcResultMatchers.status().isOk());
        }
    }

    @Test
    @WithMockUser
    void incorrectParam_in_getUser_return400() throws Exception{
        var user = userRepository.findByUsername("Adolf").orElse(null);
        if(user!=null) {
            ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                    .param("null", user.getId().toString()));

            response.andExpect(MockMvcResultMatchers.status().isBadRequest());
        }
    }

    @Test
    @WithMockUser
    void incorrect_in_deleteUser_return200() throws Exception {

        UserEntity testUser = userRepository.findByUsername("Artem").orElse(null);

        if(testUser!=null) {
            String resp = "/api/users/delete/" + testUser.getId().toString();
            ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete(resp))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

    }

    @Test
    @WithMockUser
    void deleteUser_return400() throws Exception {

        UserEntity testUser = userRepository.findByUsername("Artem").orElse(null);

        if(testUser!=null) {
            String resp = "/api/users/delete/aaa" ;
            ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete(resp))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

    }

    @Test
    void createUser_return200() throws Exception {

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                        .contentType(APPLICATION_JSON)
                        .content("{\"username\":\"Artem\", " +
                                " \"password\":\"password\" }"))
                .andDo(print())
                .andExpect(status().isOk());

        /*ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                        .contentType(APPLICATION_JSON)
                        .content("{\"username\":\"Кирилл\", " +
                                " \"password\":\"passwordTest\", " +
                                " \"email\":\"testPost@avs.com\", " +
                                " \"role\":[ \" user \" ] }"))
                .andDo(print())
                .andExpect(status().isOk());*/

    }

    @Test
    void singIn_return200() throws Exception {
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                        .contentType(APPLICATION_JSON)
                        .content("{\"username\":\"Artem\", " +
                                " \"password\":\"password\" }"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void singIn_return401() throws Exception {
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                        .contentType(APPLICATION_JSON)
                        .content("{\"username\":\"Artem\", " +
                                " \"password\":\"1oo1\" }"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void incorrectParam_in_createUser_return400() throws Exception {

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                        .contentType(APPLICATION_JSON)
                        .content("{\"usernamess\":\"Кирилл\", " +
                                " \"password\":\"passwordTest\", " +
                                " \"email\":\"testPost@avs.com\", " +
                                " \"role\":[ \"user \",\" admin\" ] }"))
                .andExpect(status().isBadRequest());

    }
}