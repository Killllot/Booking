package com.newBooking.Controllers;

import com.newBooking.DTO.Room.RoomDto;
import com.newBooking.Data.models.Room;
import com.newBooking.DTO.Room.createRoomDtoValidator;
import com.newBooking.domain.Service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import serilogj.Log;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@Validated
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity createRoom(@Valid @RequestBody createRoomDtoValidator room) {
        try{
            return ResponseEntity.ok(Room.toModel(roomService.createRoom(RoomDto.fromDtoToEntity(room))));
        }
        catch (Exception e) {
            Log.error("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Room>> getUnoccupiedRooms (@NotNull @RequestParam("FromUtc")
                                                              @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
                                                                      LocalDateTime FromUtc,
                                                          @NotNull @RequestParam("ToUtc")
                                                          @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
                                                                  LocalDateTime ToUtc) {
        try {
            return ResponseEntity.ok(roomService.getUnoccupiedRooms(FromUtc,ToUtc).stream()
                    .map(value ->new Room(value.getId(), value.getName()))
                    .collect(Collectors.toList()));
        }
        catch (Exception e) {
            Log.error("Error: " + e.getMessage());
            ResponseEntity.badRequest().body(e.getMessage());
        }
        return null;
    }
}
