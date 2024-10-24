package com.example.techtaskkrainet.services.Impl;

import com.example.techtaskkrainet.models.User;
import com.example.techtaskkrainet.repositories.UserRepository;
import com.example.techtaskkrainet.wrappers.UserDetailsWrapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Реализация интерфейса UserDetailsService для предоставления информации о пользователях
 * для аутентификационной системы Spring Security.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    // Репозиторий для работы с объектами пользователя
    private final UserRepository userRepository;

    /**
     * Загружает данные пользователя по его имени (username).
     * Этот метод используется Spring Security для аутентификации пользователя.
     *
     * @param username - имя пользователя, которое необходимо загрузить
     * @return объект UserDetails, содержащий информацию о пользователе
     * @throws UsernameNotFoundException если пользователь с данным именем не найден
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        // Ищем пользователя в базе данных по его имени (username)
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь с логином %s не найден", username)));

        // Возвращаем объект-обёртку UserDetails, который Spring Security использует для аутентификации
        return new UserDetailsWrapper(user);
    }
}
