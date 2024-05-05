package makaroshyna.onlinebookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import makaroshyna.onlinebookstore.dto.user.UserRegistrationRequestDto;
import makaroshyna.onlinebookstore.dto.user.UserResponseDto;
import makaroshyna.onlinebookstore.exception.RegistrationException;
import makaroshyna.onlinebookstore.service.user.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User authentication", description = "Endpoint for registering new users")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/registration")
    @Operation(summary = "Register a new user",
            description = "Register a new user")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }
}
