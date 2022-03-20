package Data.DTO.User;

import Data.Entity.UserEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class createUserDTO {

    private Long id;
    @NotNull
    @Size(min = 3, message = "Имя пользователя должно быть более 3-x символов")
    private String userName;

    public static createUserDTO toModel(UserEntity entity) {
        createUserDTO model = new createUserDTO();
        model.setId(entity.getId());
        model.setUserName(entity.getUserName());
        return model;
    }

    public createUserDTO() {
    }
}
