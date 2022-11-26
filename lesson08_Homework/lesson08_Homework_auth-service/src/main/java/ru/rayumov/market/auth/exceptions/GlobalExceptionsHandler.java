package ru.rayumov.market.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler
    public ResponseEntity<AppError> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new AppError("RESOURCE_NOT_FOUND", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> userAlreadyRegisteredException(UserAlreadyExistsException e) {
        return new ResponseEntity<>(new AppError("USER_ALREADY_EXISTS", e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> SamePasswordsException(NotSamePasswordsException e) {
        return new ResponseEntity<>(new AppError("NOT_SAME_PASSWORDS", e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> SamePasswordsException(EmailAlreadyExistsException e) {
        return new ResponseEntity<>(new AppError("EMAIL_ALREADY_EXISTS", e.getMessage()), HttpStatus.CONFLICT);
    }
}
