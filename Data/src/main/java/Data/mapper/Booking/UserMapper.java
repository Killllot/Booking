package Data.mapper.Booking;

import Data.DTO.User.createUserDTO;
import Data.Entity.UserEntity;

public class UserMapper {
    public static UserEntity fromDtoToEntity (createUserDTO userDTO) {
        UserEntity user = new UserEntity();
        user.setId(userDTO.getId());
        user.setUserName(userDTO.getUserName());

        return user;
    }
}
