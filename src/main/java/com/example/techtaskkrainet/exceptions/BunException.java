package com.example.techtaskkrainet.exceptions;

//Исключение, если пользователь был забанен
public class BunException extends RuntimeException {
    public BunException(String message) {
        super(message);
    }
}
