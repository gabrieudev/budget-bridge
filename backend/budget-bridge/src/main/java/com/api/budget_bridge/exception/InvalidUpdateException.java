package com.api.budget_bridge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.time.Instant;

public class InvalidUpdateException extends RuntimeException {
    public InvalidUpdateException(String message) {
        super(message);
    }

    public ProblemDetail toProblemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, super.getLocalizedMessage());
        problemDetail.setTitle("Invalid update");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("stacktrace", super.getStackTrace());
        return problemDetail;
    }
}
