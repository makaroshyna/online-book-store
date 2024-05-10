package makaroshyna.onlinebookstore.service.user;

import static makaroshyna.onlinebookstore.model.Role.RoleName.USER;

import lombok.RequiredArgsConstructor;
import makaroshyna.onlinebookstore.dto.user.UserLoginRequestDto;
import makaroshyna.onlinebookstore.dto.user.UserLoginResponseDto;
import makaroshyna.onlinebookstore.dto.user.UserRegistrationRequestDto;
import makaroshyna.onlinebookstore.dto.user.UserRegistrationResponseDto;
import makaroshyna.onlinebookstore.mapper.UserMapper;
import makaroshyna.onlinebookstore.model.User;
import makaroshyna.onlinebookstore.repository.user.UserRepository;
import makaroshyna.onlinebookstore.security.JwtUtil;
import makaroshyna.onlinebookstore.service.role.RoleService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RuntimeException("User with email " + requestDto.getEmail()
                                       + " already exists");
        }
        User user = userMapper.toModel(requestDto);
        user.setEmail(user.getEmail());
        user.setRoles(roleService.getAllByName(USER));
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserLoginResponseDto authorize(UserLoginRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(),
                        requestDto.getPassword())
        );
        String token = jwtUtil.generateToken(authentication.getName());
        return new UserLoginResponseDto(token);
    }
}
