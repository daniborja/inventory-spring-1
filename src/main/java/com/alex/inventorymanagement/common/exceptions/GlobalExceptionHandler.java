package com.alex.inventorymanagement.common.exceptions;

import com.alex.inventorymanagement.common.dto.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;


// Handler de TODAS las exceptions de nuestra App. -- basta con esto, ya NOO necesita mas nada, ni ser importado ni notificado a Spring
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {  // extends para crear el handler del @Valid

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

}
