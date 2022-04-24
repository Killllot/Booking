package com.newBooking.domain.Service;

import com.newBooking.domain.Entity.UserEntity;
import com.newBooking.domain.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Value("${const.minimumUserNameLength}")
    private long minimumNameLength;

    @Autowired
    private UserRepository userRepository;

    public UserEntity registration (UserEntity user)  {
        if(userRepository.findByUsername(user.getUsername())!=null) {
            throw  new RuntimeException("Пользователь с таким именем уже существует");
        }
        if(user.getUsername().length()< minimumNameLength) {
            throw  new RuntimeException("Имя пользователя должно быть длиннее " +
                    minimumNameLength + " символов");
        }
        return userRepository.save(user);
    }

    public UserEntity getUser(Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if(user==null) {
            throw new RuntimeException("Пользователь с таким id не найден");
        }
        return user;
    }

    public Long deleteUser(Long id) {
        userRepository.deleteById(id);
        return id;
    }
}
