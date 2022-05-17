package com.newBooking.controllers;

import com.newBooking.dto.user.UserDto;
import com.newBooking.dto.user.ValidatedUserDto;
import com.newBooking.Data.mapper.user.UserMapper;
import com.newBooking.domain.service.impl.UserServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImp userService;


    @GetMapping("/getAll")
    public ResponseEntity getAll () {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping
    public ResponseEntity getOneUser (@NotNull @RequestParam Long id) {

        return ResponseEntity.ok(UserMapper.toModel(userService.getUser(id)));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@NotNull @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Delete");
    }
}
