package com.newBooking.domain.Service;

import com.newBooking.domain.Entity.UserEntity;
import com.newBooking.domain.Repository.IUserRepository;
import com.newBooking.domain.Exeption.ConfigurationException;
import com.newBooking.domain.Exeption.UserAlreadyExistException;
import com.newBooking.domain.Exeption.UserNameShortException;
import com.newBooking.domain.Exeption.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Value("${const.minimumUserNameLength}")
    private long minimumNameLength;

    @Autowired
    private IUserRepository userRepository;

    public UserEntity registration (UserEntity user)  {
        if(userRepository.findByUserName(user.getUserName())!=null) {
            throw  new RuntimeException("Пользователь с таким именем уже существует");
        }
        if(user.getUserName().length()< minimumNameLength) {
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
