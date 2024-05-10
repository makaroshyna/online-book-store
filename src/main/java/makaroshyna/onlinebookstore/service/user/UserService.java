package makaroshyna.onlinebookstore.service.user;

import makaroshyna.onlinebookstore.dto.user.UserLoginRequestDto;
import makaroshyna.onlinebookstore.dto.user.UserLoginResponseDto;
import makaroshyna.onlinebookstore.dto.user.UserRegistrationRequestDto;
import makaroshyna.onlinebookstore.dto.user.UserRegistrationResponseDto;

public interface UserService {
    UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto);

    UserLoginResponseDto authorize(UserLoginRequestDto requestDto);
}
