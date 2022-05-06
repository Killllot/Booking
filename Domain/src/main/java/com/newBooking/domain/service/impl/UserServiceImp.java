package com.newBooking.domain.service.impl;

import com.newBooking.domain.entity.UserEntity;
import com.newBooking.domain.repository.RoleRepository;
import com.newBooking.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImp implements com.newBooking.domain.service.Interface.UserService {

    @Value("${const.minimumUserNameLength}")
    private long minimumNameLength;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;


    @Autowired
    public UserServiceImp(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserEntity registration (UserEntity user)  {
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw  new RuntimeException("Пользователь с таким именем уже существует");
        }
        if(user.getUsername().length()< minimumNameLength) {
            throw  new RuntimeException("Имя пользователя должно быть длиннее " +
                    minimumNameLength + " символов");
        }
        return userRepository.save(user);
    }

    @Override
    public List<UserEntity> getAll() {
        List<UserEntity> result = userRepository.findAll();
        log.info("IN getAll - {} users found", result);
        return result;
    }

    @Override
    public UserEntity findByUsername(String name) {
        UserEntity user = userRepository.findByUsername(name).orElse(null);
        log.info("IN findByUsername - user: {} found by username: {}",user, name);
        return user;
    }

    @Override
    public UserEntity getUser(Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        log.info("IN getUser - user: {} found by id: {}",user, id);
        if(user==null) {
            log.warn("IN getUser - no user found by id: {}", id);
            throw new RuntimeException("Пользователь с таким id не найден");
        }
        return user;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        log.info("IN deleteUser - user: found by id: {}",id);

    }
}
