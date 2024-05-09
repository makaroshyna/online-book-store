package makaroshyna.onlinebookstore.service.user;

import lombok.RequiredArgsConstructor;
import makaroshyna.onlinebookstore.dto.user.UserRegistrationRequestDto;
import makaroshyna.onlinebookstore.dto.user.UserResponseDto;
import makaroshyna.onlinebookstore.mapper.UserMapper;
import makaroshyna.onlinebookstore.model.User;
import makaroshyna.onlinebookstore.repository.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto) {
        if (userRepository.findAllByEmail(requestDto.getEmail()).isPresent()) {
            throw new RuntimeException("User with email " + requestDto.getEmail()
                                       + " already exists");
        }
        User user = userMapper.toModel(requestDto);
        return userMapper.toDto(userRepository.save(user));
    }
}
