package com.example.techtaskkrainet.services;


import com.example.techtaskkrainet.models.User;

/**
 * Сервис сервиса для работы с сущностью User.
 */
public interface UserService extends BaseService<User,User,Long> {
    User findByUsername(String username);
}
