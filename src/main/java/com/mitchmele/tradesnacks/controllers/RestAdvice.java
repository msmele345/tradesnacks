package com.mitchmele.tradesnacks.controllers;

import com.mitchmele.tradesnacks.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestAdvice {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ErrorResponse handleAllExceptions(Exception e) {
        return defaultHandler(e);
    }

    private ErrorResponse defaultHandler(Throwable t) {
        return ErrorResponse.builder()
                .exception(t.getClass().getSimpleName())
                .exceptionMsg(t.getLocalizedMessage())
                .build();
    }
}
