package com.newBooking.domain.Controllers;

import com.newBooking.Data.DTO.Booking.Booking;
import com.newBooking.Data.DTO.Booking.createBookingDTO;
import com.newBooking.domain.Exeption.BookingException;
import com.newBooking.domain.Service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import serilogj.Log;

import javax.validation.Valid;


@Slf4j
@RestController
@ComponentScan("com.newBooking")
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity createBooking(@Valid  @RequestBody createBookingDTO book) {
        try{
            return ResponseEntity.ok(Booking.toModel(bookingService.createBooking(book)));
        }
        catch (Exception e) {
            Log.error("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity getBooking (@RequestParam Long bookingId) {
        try {
            return ResponseEntity.ok(Booking.toModel(bookingService.getBooking(bookingId)));
        } catch (BookingException e) {

            Log.error("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
