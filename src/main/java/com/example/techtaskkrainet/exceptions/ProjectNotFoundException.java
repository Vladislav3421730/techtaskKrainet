package com.example.techtaskkrainet.exceptions;

//Исключение, если проект не был найден
public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String message) {
        super(message);
    }
}
