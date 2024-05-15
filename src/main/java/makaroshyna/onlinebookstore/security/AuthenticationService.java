package makaroshyna.onlinebookstore.security;

import makaroshyna.onlinebookstore.dto.user.UserLoginRequestDto;
import makaroshyna.onlinebookstore.dto.user.UserLoginResponseDto;

public interface AuthenticationService {
    UserLoginResponseDto authenticate(UserLoginRequestDto requestDto);
}
