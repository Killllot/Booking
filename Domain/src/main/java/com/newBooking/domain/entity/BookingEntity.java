package com.newBooking.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "bookings")
@AllArgsConstructor
@Builder
public class BookingEntity {
    public BookingEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fromUtc;
    private LocalDateTime toUtc;
    private String Comment;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    BookingEntity(Long id, LocalDateTime from_utc, LocalDateTime to_utc,String comment, Long room_id,Long user_id) {
        this.id=id;
        this.fromUtc = from_utc;
        this.toUtc = to_utc;
        this.Comment = comment;
        this.room= new RoomEntity(room_id,null);
        this.user= new UserEntity(user_id,null);
    }

    @Override
    public String toString() {
        return "Booking [id=" + id + ", from_utc=" + fromUtc.toString() + ", to_utc" + toUtc.toString() + ", comment=" +
                "" + Comment +", room_id=" + room.getId().toString() + ", user_id=" + user.getId().toString()+ " ]";
    }

}
