package makaroshyna.onlinebookstore.mapper;

import makaroshyna.onlinebookstore.config.MapperConfig;
import makaroshyna.onlinebookstore.dto.user.UserRegistrationRequestDto;
import makaroshyna.onlinebookstore.dto.user.UserRegistrationResponseDto;
import makaroshyna.onlinebookstore.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserRegistrationResponseDto toDto(User user);

    User toModel(UserRegistrationRequestDto requestDto);
}
