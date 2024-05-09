package makaroshyna.onlinebookstore.service.user;

import makaroshyna.onlinebookstore.dto.user.UserRegistrationRequestDto;
import makaroshyna.onlinebookstore.dto.user.UserResponseDto;

public interface UserService {
    public UserResponseDto register(UserRegistrationRequestDto requestDto);
}
