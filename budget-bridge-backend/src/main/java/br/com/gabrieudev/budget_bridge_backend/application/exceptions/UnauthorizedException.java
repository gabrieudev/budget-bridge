package br.com.gabrieudev.budget_bridge_backend.application.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
