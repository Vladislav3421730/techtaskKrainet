package com.example.techtaskkrainet.exceptions;

//Исключение, если начальная дата больше либо равна конечной
public class WrongDateException extends RuntimeException {
    public WrongDateException(String message) {
        super(message);
    }
}
