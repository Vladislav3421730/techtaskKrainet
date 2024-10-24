package com.example.techtaskkrainet.configuration;


import com.example.techtaskkrainet.utils.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.stream.Collectors;


/**
 * Фильтр для обработки JWT-токенов в запросах.
 *
 * Этот фильтр перехватывает входящие HTTP-запросы, извлекает JWT-токен
 * из заголовка авторизации, проверяет его корректность и настраивает
 * контекст безопасности Spring Security на основе извлеченной информации.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtTokenUtils jwtTokenUtils;


    /**
     * Метод, который выполняется для каждого входящего HTTP-запроса.
     *
     * @param request  входящий HTTP-запрос
     * @param response HTTP-ответ, который будет возвращен
     * @param filterChain цепочка фильтров, которую необходимо продолжить
     * @throws ServletException если происходит ошибка обработки запроса
     * @throws IOException если возникает ошибка ввода-вывода
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization"); // Извлекаем заголовок авторизации
        String username = null;
        String jwt = null;

        // Проверяем, начинается ли заголовок с "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // Извлекаем токен, удаляя "Bearer "
            try {
                username = jwtTokenUtils.getUsername(jwt); // Извлекаем имя пользователя из токена
            } catch (ExpiredJwtException e) {
                log.debug("Время жизни токена вышло"); // Логируем сообщение об истечении токена
            } catch (SignatureException e) {
                log.debug("Подпись неправильная"); // Логируем сообщение о неправильной подписи токена
            } catch (MalformedJwtException e) {
                log.debug("Некорректный токен"); // Логируем сообщение о некорректном токене
            } catch (UnsupportedJwtException e) {
                log.debug("Формат токена не поддерживается"); // Логируем сообщение о неподдерживаемом формате токена
            } catch (IllegalArgumentException e) {
                log.debug("Токен пустой или некорректный"); // Логируем сообщение о пустом или некорректном токене
            }
        }

        // Если имя пользователя извлечено и аутентификация еще не настроена
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Создаем токен аутентификации с извлеченным именем пользователя и его ролями
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    jwtTokenUtils.getRoles(jwt).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
            );
            SecurityContextHolder.getContext().setAuthentication(token); // Устанавливаем токен аутентификации в контекст безопасности
        }

        filterChain.doFilter(request, response); // Продолжаем цепочку фильтров
    }
}
