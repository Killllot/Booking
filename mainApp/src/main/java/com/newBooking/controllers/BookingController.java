package com.newBooking.controllers;

import com.newBooking.dto.booking.BookingDto;
import com.newBooking.Data.models.Booking;
import com.newBooking.dto.booking.ValidatedBookingDto;
import com.newBooking.Data.mapper.booking.BookingMapper;
import com.newBooking.domain.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@Validated
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

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

    @GetMapping("/getByPage")
    public ResponseEntity<?> getBookingByPage (@RequestParam(required = false) String sorting,
                                               @RequestParam(defaultValue = "5") Integer quantity,
                                               @RequestParam(defaultValue = "0") Integer page,
                                               @RequestParam(defaultValue = "id,desc") String[] sort) {

        List<Sort.Order> orders = new ArrayList<Sort.Order>();

        if (sort[0].contains(",")) {
            // will sort more than 2 fields
            // sortOrder="field, direction"
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            // sort=[field, direction]
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }

        Pageable paging = PageRequest.of(page,quantity,Sort.by(orders));
        return new ResponseEntity<>(bookingService.getBookingByPage(paging).stream()
                .map(BookingMapper::toModel)
                .collect(Collectors.toList()), HttpStatus.FOUND);
    }

    /*@GetMapping("/getByPage")
    public ResponseEntity<?> getBookingByPage (Pageable pageable) {
        return new ResponseEntity<>(bookingService.getBookingByPage(pageable), HttpStatus.FOUND);
    }*/
}
