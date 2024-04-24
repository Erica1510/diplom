package com.example.controllers;

import com.example.dto.ErrorResponse;
import com.example.dto.ValidationExceptionResponse;
import com.example.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder().title(e.getMessage()).details(request.getRequestURI()).build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .title(e.getMessage())
                .details(request.getRequestURI())
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleJwtAuthenticationException(JwtAuthenticationException e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .title(e.getMessage())
                .details(request.getRequestURI())
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder().title(e.getMessage()).details(request.getRequestURI()).build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(PasswordsDoesNotMatchException.class)
    public ResponseEntity<ErrorResponse> handlePasswordsDoesNotMatchException(PasswordsDoesNotMatchException e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .title(e.getMessage())
                .details(request.getRequestURI())
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .title(e.getMessage())
                .details(request.getRequestURI())
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationExceptionResponse>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ValidationExceptionResponse> validationExceptionResponses = ex.getBindingResult().getAllErrors().stream()
                .map(e -> new ValidationExceptionResponse(((FieldError) e).getField(), e.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(validationExceptionResponses, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ChatRoomNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleChatRoomNotFoundException(ChatRoomNotFoundException e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .title(e.getMessage())
                .details(request.getRequestURI())
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .title(e.getMessage())
                .details(request.getRequestURI())
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .title(e.getMessage())
                .details(request.getRequestURI())
                .build();
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}