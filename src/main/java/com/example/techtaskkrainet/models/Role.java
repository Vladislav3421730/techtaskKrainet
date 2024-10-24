package com.example.techtaskkrainet.models;

import org.springframework.security.core.GrantedAuthority;

//enum для определения ролей пользователя
public enum Role implements GrantedAuthority {

    ROLE_USER,ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
