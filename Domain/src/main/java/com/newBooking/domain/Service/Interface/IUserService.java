package com.newBooking.domain.Service.Interface;

import com.newBooking.domain.Entity.UserEntity;

import java.util.List;

public interface IUserService {
    UserEntity registration(UserEntity user);

    List<UserEntity> getAll();

    UserEntity findByUsername(String name);

    UserEntity getUser(Long id);

    void deleteUser(Long id);
}
