package com.newBooking.Controllers;

import com.newBooking.DTO.User.UserDto;
import com.newBooking.DTO.User.createUserDtoValidator;
import com.newBooking.Data.mapper.User.UserMapper;
import com.newBooking.Data.models.User;
import com.newBooking.domain.Exeption.ConfigurationException;
import com.newBooking.domain.Exeption.UserAlreadyExistException;
import com.newBooking.domain.Exeption.UserNameShortException;
import com.newBooking.domain.Exeption.UserNotFoundException;
import com.newBooking.domain.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import serilogj.Log;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity registration (@Valid @RequestBody createUserDtoValidator user) {
        userService.registration(UserDto.fromDtoToEntity(user));
        return ResponseEntity.ok("Пользователь зарегистрирован");

    }

    @GetMapping
    public ResponseEntity getOneUser (@NotNull @RequestParam Long id) {

        return ResponseEntity.ok(UserMapper.toModel(userService.getUser(id)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@NotNull @PathVariable Long id) {
        Log.error("Error: Not found deleted User with id " + id);
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
