package com.example.techtaskkrainet.dto;


import lombok.Data;

//DTO для входа в систему
@Data
public class UserDto {
    private String username;
    private String password;
}
