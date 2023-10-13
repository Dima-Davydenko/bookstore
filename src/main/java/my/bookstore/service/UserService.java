package my.bookstore.service;

import java.util.Optional;
import my.bookstore.dto.user.UserRegistrationRequest;
import my.bookstore.dto.user.UserResponseDto;
import my.bookstore.exception.RegistrationException;
import my.bookstore.model.User;

public interface UserService {
    UserResponseDto register(UserRegistrationRequest request) throws RegistrationException;

    Optional<User> findByEmail(String email);
}
