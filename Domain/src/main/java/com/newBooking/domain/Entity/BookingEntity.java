package com.newBooking.domain.Entity;

import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
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
    @JoinColumn(name = "room")
    private RoomEntity room;

}
