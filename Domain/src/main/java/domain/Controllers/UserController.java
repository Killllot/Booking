package domain.Controllers;

import Data.DTO.User.createUserDTO;
import Data.mapper.Booking.UserMapper;
import domain.Exeption.ConfigurationException;
import domain.Exeption.UserAlreadyExistException;
import domain.Exeption.UserNameShortException;
import domain.Exeption.UserNotFoundException;
import domain.Service.UserService;
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
    public ResponseEntity registration (@Valid @RequestBody createUserDTO user) {
        try {
            userService.registration(UserMapper.fromDtoToEntity(user));
            return ResponseEntity.ok("Пользователь зарегистрирован");
        }
        catch (UserAlreadyExistException | UserNameShortException | ConfigurationException e) {
            Log.error("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping
    public ResponseEntity getOneUser (@NotNull @RequestParam Long id) {
        try {
            return ResponseEntity.ok(userService.getUser(id));
        }
        catch (UserNotFoundException e) {
            Log.error("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@NotNull @PathVariable Long id) {
        Log.error("Error: Not found deleted User with id " + id);
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
