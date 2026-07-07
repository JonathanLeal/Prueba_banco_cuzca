package com.banco.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.banco.dto.ErrorResponseDTO; 

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        ErrorResponseDTO errorDTO = ErrorResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponseDTO errorDTO = ErrorResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation Failed")
                .message("Existen errores en los campos enviados")
                .timestamp(LocalDateTime.now())
                .errors(errors)
                .build();

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCredentials(BadCredentialsException ex) {
        ErrorResponseDTO errorDTO = ErrorResponseDTO.builder()
                .status(HttpStatus.UNAUTHORIZED.value()) 
                .error("Unauthorized")
                .message("Correo electrónico o contraseña incorrectos.")
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponseDTO> handleDisabledUser(DisabledException ex) {
        ErrorResponseDTO errorDTO = ErrorResponseDTO.builder()
                .status(HttpStatus.FORBIDDEN.value()) 
                .error("Forbidden")
                .message("Esta cuenta de usuario se encuentra deshabilitada.")
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorDTO, HttpStatus.FORBIDDEN);
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<com.banco.dto.ErrorResponseDTO> handleResourceNotFound(ResourceNotFoundException ex) {
        com.banco.dto.ErrorResponseDTO errorDTO = com.banco.dto.ErrorResponseDTO.builder()
                .status(org.springframework.http.HttpStatus.NOT_FOUND.value()) 
                .error("Not Found")
                .message(ex.getMessage())
                .timestamp(java.time.LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorDTO, org.springframework.http.HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex) {
        
        String message = "El cuerpo de la petición (JSON) tiene un formato inválido o un valor no permitido.";
        
        if (ex.getCause() instanceof java.lang.IllegalArgumentException) {
            message = ex.getCause().getMessage();
        }

        com.banco.dto.ErrorResponseDTO errorDTO = com.banco.dto.ErrorResponseDTO.builder()
                .status(org.springframework.http.HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(message)
                .timestamp(java.time.LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorDTO, org.springframework.http.HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(OverlappingReservationException.class)
    public ResponseEntity<ErrorResponseDTO> handleOverlappingReservation(OverlappingReservationException ex) {
        ErrorResponseDTO errorDTO = ErrorResponseDTO.builder()
                .status(HttpStatus.CONFLICT.value()) //409
                .error("Conflict")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorDTO, HttpStatus.CONFLICT);
    }
}