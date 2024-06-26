package makaroshyna.onlinebookstore.service.user;

import static makaroshyna.onlinebookstore.model.Role.RoleName.USER;

import lombok.RequiredArgsConstructor;
import makaroshyna.onlinebookstore.dto.user.UserRegistrationRequestDto;
import makaroshyna.onlinebookstore.dto.user.UserRegistrationResponseDto;
import makaroshyna.onlinebookstore.mapper.UserMapper;
import makaroshyna.onlinebookstore.model.User;
import makaroshyna.onlinebookstore.repository.user.UserRepository;
import makaroshyna.onlinebookstore.service.role.RoleService;
import makaroshyna.onlinebookstore.service.shoppingcart.ShoppingCartService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ShoppingCartService shoppingCartService;

    @Override
    public UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RuntimeException("User with email " + requestDto.getEmail()
                                       + " already exists");
        }
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail());
        user.setRoles(roleService.getAllByName(USER));
        User savedUser = userRepository.save(user);
        shoppingCartService.createShoppingCartForUser(user);
        return userMapper.toDto(savedUser);
    }
}
