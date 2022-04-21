package com.newBooking.domain.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String password;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable (name = "user_roles",
    joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName ="id")})
    private List<Role> roles;

    @OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY, mappedBy = "user")
    private List<BookingEntity> bookingEntityList;


    public UserEntity(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }

        public UserEntity() {
    }


}
