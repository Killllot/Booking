package com.newBooking.domain.Service;

import com.newBooking.domain.Entity.UserEntity;
import com.newBooking.domain.Repository.UserRepository;
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
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity registration (UserEntity user) throws UserAlreadyExistException, UserNameShortException, ConfigurationException {
        if(userRepository.findByUserName(user.getUserName())!=null) {
            throw  new UserAlreadyExistException("Пользователь с таким именем уже существует");
        }
        if(user.getUserName().length()< minimumNameLength) {
            throw  new UserNameShortException("Имя пользователя должно быть длиннее " +
                    minimumNameLength + " символов");
        }
        return userRepository.save(user);
    }

    public UserEntity getUser(Long id) throws UserNotFoundException {
        UserEntity user = userRepository.findById(id).orElse(null);
        return user;
    }

    public boolean deleteUser(Long id) {
        userRepository.deleteById(id);
        return true;
    }
}
