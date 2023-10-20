package my.bookstore.service;

import my.bookstore.dto.user.UserRegistrationRequest;
import my.bookstore.dto.user.UserResponseDto;
import my.bookstore.exception.RegistrationException;
import my.bookstore.model.User;
import org.springframework.security.core.Authentication;

public interface UserService {
    UserResponseDto register(UserRegistrationRequest request) throws RegistrationException;

    User getAuthenticatedUser(Authentication auth);
}
