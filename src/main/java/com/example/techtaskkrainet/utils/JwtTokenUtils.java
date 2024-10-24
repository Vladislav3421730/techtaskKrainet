package com.example.techtaskkrainet.utils;

import com.example.techtaskkrainet.models.Role;
import com.example.techtaskkrainet.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Вспомогательный класс для работы с JWT токенами.
 * Реализует логику генерации токена, извлечения данных из токена и валидации.
 */
@Component
public class JwtTokenUtils {

    // Секретный ключ для подписи JWT токенов, извлекаемый из файла настроек.
    @Value("${jwt.secret}")
    private String secret;

    // Время жизни токена (настраивается через properties).
    @Value("${jwt.lifetime}")
    private Duration jwtLifetime;

    /**
     * Генерация JWT токена на основе информации о пользователе.
     * В токен добавляются роли пользователя, время выпуска и время истечения.
     *
     * @param user объект пользователя для генерации токена
     * @return строка JWT токена
     */
    public String generateToken(User user) {
        // Создаём дополнительные данные (claims), которые будут помещены в токен
        Map<String, Object> claims = new HashMap<>();

        // Извлекаем список ролей пользователя
        List<String> rolesList = user.getRoles().stream()
                .map(Role::getAuthority) // Преобразуем объекты Role в строки
                .collect(Collectors.toList());

        // Добавляем роли в claims токена
        claims.put("roles", rolesList);

        // Устанавливаем время выпуска токена
        Date issuedDate = new Date();

        // Устанавливаем время истечения токена
        Date expiredDate = new Date(issuedDate.getTime() + jwtLifetime.toMillis());

        // Формируем и подписываем JWT токен с использованием алгоритма HS256
        return Jwts.builder()
                .setClaims(claims) // Добавляем claims (доп. данные)
                .setSubject(user.getUsername()) // Устанавливаем имя пользователя как subject токена
                .setIssuedAt(issuedDate) // Указываем время выпуска токена
                .setExpiration(expiredDate) // Указываем время истечения токена
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256) // Подписываем токен
                .compact(); // Генерируем окончательную строку токена
    }

    /**
     * Извлечение имени пользователя (username) из токена.
     *
     * @param token строка JWT токена
     * @return имя пользователя (username), указанное в токене
     */
    public String getUsername(String token) {
        // Получаем claims и возвращаем subject, который является именем пользователя
        return getAllClaimsFromToken(token).getSubject();
    }

    /**
     * Извлечение ролей пользователя из токена.
     *
     * @param token строка JWT токена
     * @return список ролей, указанных в токене
     */
    public List<String> getRoles(String token) {
        // Получаем claims и возвращаем роли, которые были записаны при генерации токена
        return getAllClaimsFromToken(token).get("roles", List.class);
    }

    /**
     * Извлечение всех claims (данных) из токена.
     * Используется для получения полной информации из токена.
     *
     * @param token строка JWT токена
     * @return claims (данные) токена
     */
    private Claims getAllClaimsFromToken(String token) {
        // Парсим токен и возвращаем его claims
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())) // Указываем секретный ключ для валидации подписи токена
                .build()
                .parseClaimsJws(token) // Парсим и проверяем подпись токена
                .getBody(); // Возвращаем тело (claims) токена
    }
}
