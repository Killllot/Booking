package com.newBooking.Data.models;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class Booking {

    private Long id;
    private LocalDateTime fromUtc;
    private LocalDateTime toUtc;
    private String Comment;
    private Map<Long,String> rooms;

}
