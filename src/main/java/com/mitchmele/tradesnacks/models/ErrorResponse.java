package com.mitchmele.tradesnacks.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private String exceptionMsg;
    private String statusCode;
    private String exception;
}
