package com.example.techtaskkrainet.exceptions;

//Исключение, если регистрация не прошла
public class RegistrationFailedException extends RuntimeException {
    public RegistrationFailedException(String message) {
        super(message);
    }
}
