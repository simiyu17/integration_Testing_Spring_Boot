package com.crud.exceptions;

import com.crud.student.exception.StudentNotFound;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String TIME_STAMP = "timestamp";


    @ExceptionHandler({ StudentNotFound.class })
    public ErrorResponse handleAuthenticationUserNotFoundException(StudentNotFound ex) {
        return ErrorResponse.builder(ex, HttpStatus.NOT_FOUND, ex.getMessage())
                .title("Student Not Found")
                .property(TIME_STAMP, Instant.now())
                .build();
    }


    @ExceptionHandler(value = { PropertyReferenceException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handlePropertyReferenceException(PropertyReferenceException ex) {
        return ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, ex.getMessage())
                .title("Unknown Property")
                .property(TIME_STAMP, Instant.now())
                .build();
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse unCaughtRuntimeException(RuntimeException ex){
        ex.printStackTrace();
        return ErrorResponse.builder(ex, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage())
                .title("Error Occurred")
                .property(TIME_STAMP, Instant.now())
                .build();
    }


}
