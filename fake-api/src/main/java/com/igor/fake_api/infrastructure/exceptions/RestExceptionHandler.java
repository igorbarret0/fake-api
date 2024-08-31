package com.igor.fake_api.infrastructure.exceptions;

import com.igor.fake_api.infrastructure.exceptions.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductAlreadyExistsException.class)
    private ResponseEntity<ErrorResponse> ProductAlreadyExistsHandler(ProductAlreadyExistsException exception,
                                                                      HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(

                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(ProductUnexistException.class)
    private ResponseEntity<ErrorResponse> ProductUnexistHandler(ProductUnexistException exception,
                                                                HttpServletRequest request) {


        ErrorResponse errorResponse = new ErrorResponse(

                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorResponse> handleGlobalException(Exception exception,
                                                                HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(

                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "An unexpected error occurred: " + exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}
