package com.newBooking.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;

@Data
@Entity(name = "rooms")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 1, message = "id must be more 0")
    private Long id;
    private String name;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    List<BookingEntity> bookings;

    public RoomEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }




}
