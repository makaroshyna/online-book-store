package makaroshyna.onlinebookstore.service.user;

import makaroshyna.onlinebookstore.dto.user.UserRegistrationRequestDto;
import makaroshyna.onlinebookstore.dto.user.UserRegistrationResponseDto;

public interface UserService {
    public UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto);
}
