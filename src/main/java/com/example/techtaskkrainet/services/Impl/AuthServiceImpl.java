package com.example.techtaskkrainet.services.Impl;

import com.example.techtaskkrainet.dto.JwtResponseDto;
import com.example.techtaskkrainet.dto.ResponseDto;
import com.example.techtaskkrainet.dto.UserDto;
import com.example.techtaskkrainet.exceptions.BunException;
import com.example.techtaskkrainet.exceptions.LoginFailedException;
import com.example.techtaskkrainet.exceptions.RegistrationFailedException;
import com.example.techtaskkrainet.models.User;
import com.example.techtaskkrainet.repositories.UserRepository;
import com.example.techtaskkrainet.services.AuthService;
import com.example.techtaskkrainet.services.UserService;
import com.example.techtaskkrainet.utils.JwtTokenUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * Сервис для управления аутентификацией и регистрацией пользователей.
 * Предоставляет методы для генерации токенов и регистрации новых пользователей.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    // Компоненты для управления токенами, аутентификацией и работой с пользователями
    private final JwtTokenUtils jwtTokenUtils; // Утилита для работы с JWT токенами
    private final AuthenticationManager authenticationManager; // Менеджер аутентификации пользователей
    private final UserRepository userRepository; // Репозиторий для работы с пользователями в БД
    private final UserService userService; // Сервис для управления данными пользователей


    @Override
    public ResponseEntity<JwtResponseDto> createAuthToken(UserDto userDto) {
        try {
            // Аутентифицируем пользователя с помощью UsernamePasswordAuthenticationToken
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword())
            );
        } catch (BadCredentialsException badCredentialsException) {
            // Обработка исключения: если введены неверные логин или пароль
            throw new LoginFailedException(String.format("Неверный логин или пароль %s %s", userDto.getUsername(), userDto.getPassword()));
        } catch (DisabledException e) {
            // Обработка исключения: если пользователь заблокирован (например, администратором)
            throw new BunException(String.format("Пользователь %s был забанен", userDto.getUsername()));
        }

        // Если аутентификация прошла успешно, находим пользователя в базе данных
        User user = userRepository.findByUsername(userDto.getUsername()).get();

        // Генерируем JWT токен и возвращаем его в ответе
        return ResponseEntity.ok(new JwtResponseDto(jwtTokenUtils.generateToken(user)));
    }


    @Override
    @Transactional
    public ResponseEntity<ResponseDto> registerUser(User user) {
        // Проверяем, существует ли уже пользователь с таким логином
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            // Если пользователь уже существует, выбрасываем исключение
            throw new RegistrationFailedException(String.format("Пользователь с логином %s уже есть в системе", user.getUsername()));
        }
        userService.save(user);
        return ResponseEntity.ok(new ResponseDto("Пользователь сохранён"));
    }
}
