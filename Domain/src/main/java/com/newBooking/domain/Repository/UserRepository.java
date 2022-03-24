package com.newBooking.domain.Repository;

import com.newBooking.domain.Entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUserName (String username);
}
