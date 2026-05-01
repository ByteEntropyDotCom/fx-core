package com.byteentropy.fx_core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles specific Business Logic Failures (Liquidity limits, Arbirtrage, etc.)
     */
    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleBusinessLogicException(RuntimeException ex, WebRequest request) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, 
                ex.getMessage()
        );
        problem.setTitle("FX Execution Blocked");
        problem.setType(URI.create("https://api.byteentropy.com/errors/business-logic"));
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    /**
     * Handles Validation failures (Negative amounts, same currency pairs)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleValidationException(IllegalArgumentException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNPROCESSABLE_ENTITY, 
                ex.getMessage()
        );
        problem.setTitle("Invalid Request Parameters");
        problem.setType(URI.create("https://api.byteentropy.com/errors/validation"));
        return problem;
    }

    /**
     * Catch-all for unexpected system failures
     */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "An unexpected internal error occurred."
        );
        problem.setTitle("System Error");
        return problem;
    }
}