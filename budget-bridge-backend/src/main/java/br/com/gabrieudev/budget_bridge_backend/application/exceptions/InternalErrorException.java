package br.com.gabrieudev.budget_bridge_backend.application.exceptions;

public class InternalErrorException extends RuntimeException {
    public InternalErrorException(String message) {
        super(message);
    }
}
