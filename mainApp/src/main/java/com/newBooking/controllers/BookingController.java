package com.newBooking.controllers;

import com.newBooking.dto.booking.BookingDto;
import com.newBooking.Data.models.Booking;
import com.newBooking.dto.booking.ValidatedBookingDto;
import com.newBooking.Data.mapper.booking.BookingMapper;
import com.newBooking.domain.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@RestController
@Validated
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity createBooking(@Valid  @RequestBody ValidatedBookingDto book) {
            return ResponseEntity
                    .ok(BookingMapper.toModel(bookingService.createBooking(BookingDto.fromDtoToEntity(book))));
    }

    @GetMapping
    public ResponseEntity<Booking> getBooking (@RequestParam Long bookingId) {
                    return new ResponseEntity<>(BookingMapper.toModel(bookingService.getBooking(bookingId)), HttpStatus.FOUND);
    }

    @GetMapping("/test")
    public ResponseEntity testMess () {
        return ResponseEntity.ok("This is message");
    }
}
