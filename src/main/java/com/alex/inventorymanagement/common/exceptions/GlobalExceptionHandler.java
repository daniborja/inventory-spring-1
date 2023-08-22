package com.alex.inventorymanagement.common.exceptions;

import com.alex.inventorymanagement.common.dto.ErrorDetails;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


// Handler de TODAS las exceptions de nuestra App. -- basta con esto, ya NOO necesita mas nada, ni ser importado ni notificado a Spring
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {  // extends para crear el handler del @Valid

    // // Authentication
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetails> handlerBadCredentialsException(
            BadCredentialsException exception,
            WebRequest webRequest
    ) {

        ErrorDetails errorDetails = ErrorDetails.builder()
                .timeStamp(new Date())
                .message("There was a problem logging in. Check your email and password or create an account")
                .details(webRequest.getDescription(false))
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    // // Authorization
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handlerBadCredentialsException(
            AccessDeniedException exception,
            WebRequest webRequest
    ) {

        ErrorDetails errorDetails = ErrorDetails.builder()
                .timeStamp(new Date())
                .message("User without required permissions!")
                .details(webRequest.getDescription(false))
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    // // UsernameNotFoundException
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorDetails> handlerUsernameNotFoundException(
            UsernameNotFoundException exception,
            WebRequest webRequest
    ) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timeStamp(new Date())
                .message("There was a problem logging in. Check your email and password or create an account")
                .details(webRequest.getDescription(false))
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // Signature Exception NO lo atrapa, Ver como implementarlo <- throw error en el JwtService?


    // // User not found exception
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> handlerUserNotFoundException(
            UserNotFoundException exception,
            WebRequest webRequest
    ) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timeStamp(new Date())
                .message(exception.getMessage())
                .details(webRequest.getDescription(false))
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // // Not found exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handlerResourceNotFoundException(
            ResourceNotFoundException exception,
            WebRequest webRequest
    ) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timeStamp(new Date())
                .message(exception.getMessage())
                .details(webRequest.getDescription(false))
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // // Bad request exception
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetails> handlerBadRequestException(
            BadRequestException exception,
            WebRequest webRequest
    ) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timeStamp(new Date())
                .message(exception.getMessage())
                .details(webRequest.getDescription(false))
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // // Default Exception (for ALL others)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handlerGlobalException(Exception exception, WebRequest webRequest) {

        ErrorDetails errorDetails = ErrorDetails.builder()
                .timeStamp(new Date())
                .message(exception.getMessage())
                .details(webRequest.getDescription(false))
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // // Spring Validation (@Valid): handler de los errors del @Valid <- BindingResult
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName = ((FieldError) err).getField();
            String message = err.getDefaultMessage();

            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, status);
    }

}
