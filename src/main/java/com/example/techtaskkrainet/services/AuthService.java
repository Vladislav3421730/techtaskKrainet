package com.example.techtaskkrainet.services;

import com.example.techtaskkrainet.dto.JwtResponseDto;
import com.example.techtaskkrainet.dto.ResponseDto;
import com.example.techtaskkrainet.dto.UserDto;
import com.example.techtaskkrainet.models.User;
import org.springframework.http.ResponseEntity;
/**
*Сервис для авторизации и регистрации
*/
public interface AuthService  {

    /**
     * Метод для аутентификации пользователя и создания JWT токена
     * @param userDto - DTO с данными для аутентификации (логин и пароль)
     * @return ResponseEntity с JWT токеном
     */
    ResponseEntity<JwtResponseDto> createAuthToken(UserDto userDto);

    /**
     * Метод для регистрации нового пользователя
     * @param user - объект пользователя, который нужно зарегистрировать
     * @return ResponseEntity с информацией об успешной регистрации
     */
    ResponseEntity<ResponseDto> registerUser(User user);
}
