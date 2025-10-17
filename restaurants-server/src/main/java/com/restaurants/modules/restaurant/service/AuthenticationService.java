package com.restaurants.modules.restaurant.service;

import com.restaurants.api.modules.restaurant.request.AuthenticationRequest;
import com.restaurants.api.modules.restaurant.request.AuthenticationResponse;
import com.restaurants.api.modules.restaurant.request.RegisterRequest;
import com.restaurants.modules.restaurant.entity.Role;
import com.restaurants.modules.restaurant.entity.User;
import com.restaurants.modules.restaurant.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        // Проверяем, не существует ли уже пользователь с таким логином
        if (userRepository.findByLogin(request.getLogin()).isPresent()) {
            throw new RuntimeException("User with this login already exists");
        }

        var user = User.builder()
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(Role.USER) // По умолчанию USER
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole().name())
                .login(user.getLogin())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),  // Используем login
                        request.getPassword()
                )
        );

        var user = userRepository.findByLogin(request.getLogin())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole().name())
                .login(user.getLogin())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    // Метод для создания администратора при старте приложения
    @PostConstruct
    public void createAdminUser() {
        try {
            if (!userRepository.existsByLogin("admin")) {
                User admin = User.builder()
                        .login("admin")
                        .password(passwordEncoder.encode("admin123")) // Пароль: admin123
                        .firstName("System")
                        .lastName("Administrator")
                        .role(Role.ADMIN)
                        .build();
                userRepository.save(admin);
                System.out.println("✅ Администратор создан: login=admin, password=admin123");
            } else {
                System.out.println("ℹ️ Администратор уже существует");
            }
        } catch (Exception e) {
            System.err.println("❌ Ошибка при создании администратора: " + e.getMessage());
        }
    }
}
