package com.neobis.onlinestore.exception.handler;

import com.neobis.onlinestore.exception.NotFoundException;
import com.neobis.onlinestore.exception.reponse.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestControllerAdvice
public class MainHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse notFoundException(NotFoundException e) { // если сущность (user,product, etc...) не была найдена
        return new ExceptionResponse(HttpStatus.NOT_FOUND, e.getClass().getName(), e.getMessage());
    }
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ExceptionResponse> onConstraintValidationException(
            ConstraintViolationException e
    ) {
        return e.getConstraintViolations().stream()
                .map(
                        violation -> new ExceptionResponse(
                                HttpStatus.BAD_REQUEST,
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .toList();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ExceptionResponse> onMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        return e.getBindingResult().getFieldErrors().stream()
                .map(error -> new ExceptionResponse(
                        HttpStatus.BAD_REQUEST,
                        error.getField(),
                        error.getDefaultMessage()))
                .collect(toList());
    }

}
