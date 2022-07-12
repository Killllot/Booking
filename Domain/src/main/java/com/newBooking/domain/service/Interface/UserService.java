package com.newBooking.domain.service.Interface;

import com.newBooking.domain.entity.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> getAll();

    UserEntity getUser(Long id);

    void deleteUser(Long id);
}
