package com.newBooking.domain.Service;


import com.newBooking.domain.Entity.UserEntity;
import com.newBooking.domain.Exeption.ConfigurationException;
import com.newBooking.domain.Exeption.UserAlreadyExistException;
import com.newBooking.domain.Exeption.UserNameShortException;
import com.newBooking.domain.Exeption.UserNotFoundException;
import com.newBooking.domain.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void registration() throws UserAlreadyExistException, UserNameShortException, ConfigurationException {
        UserEntity user = new UserEntity(
                1l,
                "Ivan"
        );

        userService.registration(user);
        ArgumentCaptor<UserEntity> userEntityArgumentCaptor = ArgumentCaptor.forClass(UserEntity.class);

        verify(userRepository).save(userEntityArgumentCaptor.capture());

        UserEntity capturedUser = userEntityArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void getUser() throws UserNotFoundException, UserAlreadyExistException, UserNameShortException, ConfigurationException {
        final Long id = 1l;
        final String name = "Ivan";
        userService.getUser(id);
        verify(userRepository).findById(id);


    }

    @Test
    void deleteUser() {
        final Long id = 1l;
        userService.deleteUser(id);
        verify(userRepository).deleteById(id);
    }
}