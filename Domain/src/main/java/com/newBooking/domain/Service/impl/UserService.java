package com.newBooking.domain.Service.impl;

import com.newBooking.domain.Entity.UserEntity;
import com.newBooking.domain.Repository.RoleRepository;
import com.newBooking.domain.Repository.UserRepository;
import com.newBooking.domain.Service.Interface.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService implements IUserService {

    @Value("${const.minimumUserNameLength}")
    private long minimumNameLength;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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
        /*Role role = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);

        UserEntity registerUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered",registerUser);
        return registerUser;*/
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
