package com.example.techtaskkrainet.configuration;

import com.example.techtaskkrainet.services.Impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Указывает, что этот класс содержит конфигурацию Spring
@EnableWebSecurity // Включает безопасность веб-приложения
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsServiceImpl userDetailsService; // Сервис для загрузки данных пользователя
    private final JwtRequestFilter jwtRequestFilter; // Фильтр для обработки JWT

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // Отключаем CSRF (Cross-Site Request Forgery)
                .csrf(AbstractHttpConfigurer::disable)
                // Отключаем CORS (Cross-Origin Resource Sharing)
                .cors(AbstractHttpConfigurer::disable)
                // Настраиваем правила авторизации запросов
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/project/get/**", "/record/get/**").authenticated() // Эти маршруты требуют аутентификации
                        .requestMatchers("/project/**", "/register", "/user/**").hasRole("ADMIN") // Эти маршруты доступны только администраторам
                        .requestMatchers("/record/**").hasRole("USER") // Эти маршруты доступны только пользователям
                        .anyRequest().permitAll()) // Все остальные запросы разрешены без аутентификации
                // Устанавливаем политику управления сессиями как Stateless (без состояния)
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Добавляем фильтр для обработки JWT перед фильтром аутентификации
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build(); // Строим конфигурацию
    }

    @Bean // Создаем бин для BCryptPasswordEncoder
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Возвращаем новый экземпляр BCryptPasswordEncoder
    }

    @Bean // Создаем бин для провайдера аутентификации DAO
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); // Устанавливаем кодировщик паролей
        daoAuthenticationProvider.setUserDetailsService(userDetailsService); // Устанавливаем сервис для загрузки данных пользователя
        return daoAuthenticationProvider; // Возвращаем провайдер аутентификации
    }

    @Bean // Создаем бин для менеджера аутентификации
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // Получаем и возвращаем менеджер аутентификации
    }
}
