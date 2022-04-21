package com.newBooking.domain.Repository;

import com.newBooking.domain.Entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IUserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUserName (String username);
    List<UserEntity> findAll();
}
