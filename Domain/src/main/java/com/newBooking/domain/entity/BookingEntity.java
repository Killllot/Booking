package com.newBooking.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "bookings")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fromUtc;
    private LocalDateTime toUtc;
    private String Comment;
    //private Long roomId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    /*@ManyToMany
    @Column(name = "room")
    @JoinTable(
            name = "booking_room",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))*/
    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity room;

}
