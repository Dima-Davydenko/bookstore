package my.bookstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.bookstore.dto.user.UserLoginRequestDto;
import my.bookstore.dto.user.UserLoginResponseDto;
import my.bookstore.dto.user.UserRegistrationRequest;
import my.bookstore.dto.user.UserResponseDto;
import my.bookstore.exception.RegistrationException;
import my.bookstore.security.AuthenticationService;
import my.bookstore.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequest request)
            throws RegistrationException {
        return userService.register(request);
    }
}
