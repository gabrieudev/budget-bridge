package com.api.budget_bridge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.time.Instant;

public class InvalidCostException extends RuntimeException {
    public InvalidCostException(String message) {
        super(message);
    }

    public ProblemDetail toProblemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, super.getLocalizedMessage());
        problemDetail.setTitle("Invalid cost");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("stacktrace", super.getStackTrace());
        return problemDetail;
    }
}
