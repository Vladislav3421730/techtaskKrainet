package com.example.techtaskkrainet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<AppError> handleLoginFailedException(LoginFailedException loginFailedException){
        return new ResponseEntity<>(new AppError(loginFailedException.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RegistrationFailedException.class)
    public ResponseEntity<AppError> handleRegistrationException(RegistrationFailedException registrationFailedException){
        return new ResponseEntity<>(new AppError(registrationFailedException.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BunException.class)
    public ResponseEntity<AppError> handleBunException(BunException bunException){
        return new ResponseEntity<>(new AppError(bunException.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<AppError> handleLoginProjectNotFoundException(ProjectNotFoundException projectNotFoundException){
        return new ResponseEntity<>(new AppError(projectNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<AppError> handleLoginUsernameNotFoundException(UsernameNotFoundException usernameNotFoundException){
        return new ResponseEntity<>(new AppError(usernameNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<AppError> handleLoginUserNotFoundException(UserNotFoundException userNotFoundException){
        return new ResponseEntity<>(new AppError(userNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<AppError> handleRecordNotFoundException(RecordNotFoundException recordNotFoundException){
        return new ResponseEntity<>(new AppError(recordNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongDateException.class)
    public ResponseEntity<AppError> handleWrongDateException(WrongDateException wrongDateException){
        return new ResponseEntity<>(new AppError(wrongDateException.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
