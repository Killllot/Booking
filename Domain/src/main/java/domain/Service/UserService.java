package domain.Service;



import Data.DTO.User.User;
import Data.Entity.UserEntity;
import Data.Repository.UserRepository;
import domain.Exeption.ConfigurationException;
import domain.Exeption.UserAlreadyExistException;
import domain.Exeption.UserNameShortException;
import domain.Exeption.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Value("${const.minimumUserNameLength}")
    private long minimumNameLength;

    @Autowired
    private UserRepository userRepository;

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

    public User getUser(Long id) throws UserNotFoundException {
        UserEntity user = userRepository.findById(id).orElse(null);
        if(user==null) {
            throw new UserNotFoundException("Пользователь с таким id не найден");
        }
        return User.toModel(user);
    }

    public Long deleteUser(Long id) {
        userRepository.deleteById(id);
        return id;
    }
}
