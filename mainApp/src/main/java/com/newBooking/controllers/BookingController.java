package com.newBooking.controllers;

import com.newBooking.Data.mapper.booking.PageMapper;
import com.newBooking.Data.models.ViewPage;
import com.newBooking.domain.entity.BookingEntity;
import com.newBooking.dto.booking.BookingDto;
import com.newBooking.Data.models.Booking;
import com.newBooking.dto.booking.ValidatedBookingDto;
import com.newBooking.Data.mapper.booking.BookingMapper;
import com.newBooking.domain.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@Validated
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity createBooking(@Valid  @RequestBody ValidatedBookingDto book) {
            return ResponseEntity
                    .ok(BookingMapper.toModel(bookingService.createBooking(BookingDto.fromDtoToEntity(book))));
    }

    @GetMapping("/get")
    public ResponseEntity<Booking> getBooking (@RequestParam Long bookingId) {
        return new ResponseEntity<>(BookingMapper.toModel(bookingService.getBooking(bookingId)), HttpStatus.FOUND);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Booking>> getAll() {
        return new ResponseEntity<>(bookingService.getAll().stream()
                .map(BookingMapper::toModel)
                .collect(Collectors.toList()),HttpStatus.FOUND);
    }

    /*@GetMapping("/getByPage")
    public ResponseEntity<List<BookingEntity>> getBookingByPage (@RequestParam Long quantity, @RequestParam Long page) {
        return new ResponseEntity<>(bookingService.getBookingByPage(quantity,page), HttpStatus.FOUND);
    }*/

    @GetMapping("/getByPage")
    public ResponseEntity<?> getBookingByPage (Pageable pageable) {
        return new ResponseEntity<>(bookingService.getBookingByPage(pageable), HttpStatus.FOUND);
    }
}
