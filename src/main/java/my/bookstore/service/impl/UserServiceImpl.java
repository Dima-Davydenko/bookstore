package my.bookstore.service.impl;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import my.bookstore.dto.user.UserRegistrationRequest;
import my.bookstore.dto.user.UserResponseDto;
import my.bookstore.exception.RegistrationException;
import my.bookstore.mapper.UserMapper;
import my.bookstore.model.Role;
import my.bookstore.model.User;
import my.bookstore.repository.role.RoleRepository;
import my.bookstore.repository.user.UserRepository;
import my.bookstore.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequest request)
            throws RegistrationException {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RegistrationException("User with this email is already exist.");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setShippingAddress(request.getShippingAddress());
        user.setRoles(Set.of(roleRepository.findByRoleName(Role.RoleName.USER).get()));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
}
