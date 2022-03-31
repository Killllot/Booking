package com.newBooking.Controllers;

import com.newBooking.DTO.Booking.BookingDto;
import com.newBooking.Data.models.Booking;
import com.newBooking.DTO.Booking.createBookingDtoValidator;
import com.newBooking.Data.mapper.Booking.BookingMapper;
import com.newBooking.domain.Exeption.BookingException;
import com.newBooking.domain.Service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import serilogj.Log;

import javax.validation.Valid;


@Slf4j
@RestController
@Validated
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity createBooking(@Valid  @RequestBody createBookingDtoValidator book) {
        try{
            return ResponseEntity.ok(BookingMapper.toModel(bookingService.createBooking(BookingDto.fromDtoToEntity(book))));
        }
        catch (Exception e) {
            Log.error("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @PostMapping
//    public ResponseEntity createBooking(@Valid  @RequestBody createBookingDtoValidator book) {
//        try{
//            return ResponseEntity.ok(Booking.toModel(bookingService.createBooking(book)));
//        }
//        catch (Exception e) {
//            Log.error("Error: " + e.getMessage());
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @GetMapping
    public ResponseEntity getBooking (@RequestParam Long bookingId) {
        try {
            return ResponseEntity.ok(BookingMapper.toModel(bookingService.getBooking(bookingId)));
        } catch (BookingException e) {

            Log.error("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
