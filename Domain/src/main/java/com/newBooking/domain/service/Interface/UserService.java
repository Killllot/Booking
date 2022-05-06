package com.newBooking.domain.service.Interface;

import com.newBooking.domain.entity.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity registration(UserEntity user);

    List<UserEntity> getAll();

    UserEntity findByUsername(String name);

    UserEntity getUser(Long id);

    void deleteUser(Long id);
}
