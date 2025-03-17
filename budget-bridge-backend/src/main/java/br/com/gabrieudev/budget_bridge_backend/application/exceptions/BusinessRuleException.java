package br.com.gabrieudev.budget_bridge_backend.application.exceptions;

public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message) {
        super(message);
    }
}
