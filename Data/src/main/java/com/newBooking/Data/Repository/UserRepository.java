package com.newBooking.Data.Repository;

import com.newBooking.Data.Entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUserName (String username);
}
