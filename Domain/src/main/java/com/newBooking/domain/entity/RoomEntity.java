package com.newBooking.domain.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;

@Data
@Entity
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 1, message = "id must be more 0")
    private Long id;
    private String name;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    List<BookingEntity> bookingEntityList;

    public RoomEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public RoomEntity() {
    }


}
