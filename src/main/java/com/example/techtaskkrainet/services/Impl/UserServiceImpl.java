package com.example.techtaskkrainet.services.Impl;

import com.example.techtaskkrainet.exceptions.UserNotFoundException;
import com.example.techtaskkrainet.models.Role;
import com.example.techtaskkrainet.models.User;
import com.example.techtaskkrainet.repositories.UserRepository;
import com.example.techtaskkrainet.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Реализация интерфейса UserService для управления данными пользователей.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // Кодировщик паролей для обеспечения безопасности хранения паролей
    private final PasswordEncoder passwordEncoder;

    // Репозиторий для взаимодействия с базой данных
    private final UserRepository userRepository;


    @Override
    @Transactional
    public User save(User user) {
        user.setActive(true); // Активируем пользователя по умолчанию
        user.setRoles(Set.of(Role.ROLE_USER)); // Назначаем роль пользователя
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Кодируем пароль перед сохранением
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("Пользователь с id %d не найден", id)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(String.format("Пользователь с id %d не найден. Удаление невозможно", id));
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public User update(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new UserNotFoundException(String.format("Пользователь с id %d не найден. Обновление невозможно", user.getId()));
        }

        // Получаем текущего пользователя из базы данных для сравнения
        User updatedUser = findById(user.getId());

        // Проверяем, был ли изменён пароль, и кодируем его, если это так
        if (!updatedUser.getPassword().equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // Сохраняем обновлённого пользователя
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь с логином %s не найден", username)));
    }
}
