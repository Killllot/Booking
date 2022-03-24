package com.newBooking.domain.Entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Entity
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 1, message = "id must be more 0")
    private Long id;
    @NotBlank(message = "can't be blank")
    private String name;


    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "roomEntityList")
    List<BookingEntity> bookingEntityList;

    public RoomEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public RoomEntity() {
    }


}
