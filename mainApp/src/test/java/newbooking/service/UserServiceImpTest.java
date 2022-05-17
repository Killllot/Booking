package newbooking.service;

import com.newBooking.controllers.BookingController;
import com.newBooking.controllers.RoomController;
import com.newBooking.controllers.UserController;
import com.newBooking.domain.entity.UserEntity;
import com.newBooking.domain.entity.security.ERole;
import com.newBooking.domain.entity.security.Role;
import com.newBooking.domain.repository.UserRepository;
import newbooking.testCont.Postgres;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;


import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest(classes = {com.newBooking.api.BookingApp.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
@AutoConfigureMockMvc
class UserServiceImpTest extends Postgres {

    @Autowired
    private UserController userController;

    @Autowired
    private RoomController roomController;

    @Autowired
    private BookingController bookingController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser
    void getAll() throws Exception{
        userRepository.deleteAll();

        List<UserEntity> users = List.of(
                UserEntity.builder().username("Artem").email("sasd@ad.com").password("password").roles(Set.of(new Role(ERole.ROLE_USER))).build(),
                UserEntity.builder().username("Andrea").email("adwwq@ad.com").password("password1").roles(Set.of(new Role(ERole.ROLE_ADMIN))).build());

        userRepository.saveAll(users);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/users/getAll"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(jsonPath("$.size()", CoreMatchers.is(users.size())));

    }

    @Test
    @WithMockUser
    void getUser() throws Exception{
        userRepository.deleteAll();

        List<UserEntity> users = List.of(
                UserEntity.builder().username("Artem").email("sasd@ad.com").password("password").roles(Set.of(new Role(ERole.ROLE_USER))).build(),
                UserEntity.builder().username("Andrea").email("adwwq@ad.com").password("password1").roles(Set.of(new Role(ERole.ROLE_ADMIN))).build());

        userRepository.saveAll(users);
        UserEntity testUser = userRepository.getById(users.get(1).getId());
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/users?id=" + testUser.getId()));

        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(jsonPath("$.size()", CoreMatchers.is(1)));

    }

    @Test
    @WithMockUser
    void deleteUser() throws Exception {

        userRepository.deleteAll();
        List<UserEntity> users = List.of(
                UserEntity.builder().username("Artem").email("sasd@ad.com").password("password").roles(Set.of(new Role(ERole.ROLE_USER))).build(),
                UserEntity.builder().username("Andrea").email("adwwq@ad.com").password("password1").roles(Set.of(new Role(ERole.ROLE_ADMIN))).build());

        userRepository.saveAll(users);
        Optional<UserEntity> testUser = userRepository.findByUsername(users.get(1).getUsername());
        if(testUser.isPresent()) {
            ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/delete/" + testUser.get().getId()));

            response.andExpect(MockMvcResultMatchers.status().isOk());
        }
    }
}