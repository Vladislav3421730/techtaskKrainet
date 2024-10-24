package com.example.techtaskkrainet.controllers;
import com.example.techtaskkrainet.dto.JwtResponseDto;
import com.example.techtaskkrainet.dto.ResponseDto;
import com.example.techtaskkrainet.dto.UserDto;
import com.example.techtaskkrainet.models.User;
import com.example.techtaskkrainet.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AuthController {

    public final AuthService authService;

    /**
     * Чтобы войти в систему с ролью Admin:
     * {
     *     "username" : "Admin",
     *     "password" : "q1w2e3"
     * }
     * Чтобы войти в систему с ролью User:
     * {
     *     "username" : "User",
     *     "password" : "q1w2e3"
     * }
     * Чтобы войти в систему с ролью Admin и User:
     * {
     *     "username" : "UserAndAdmin",
     *     "password" : "q1w2e3"
     * }
     * Затем вставить токе в Authorization Bearer Token (если тестируете в Postman)
     */

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> createToken(@RequestBody UserDto userDto){
        return authService.createAuthToken(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> Registration(@RequestBody User user){
        return authService.registerUser(user);
    }



}
