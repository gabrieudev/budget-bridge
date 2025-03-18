package br.com.gabrieudev.budget_bridge_backend.adapters.input.rest;

import java.util.Objects;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.ApiResponseDTO;
import br.com.gabrieudev.budget_bridge_backend.application.exceptions.BusinessRuleException;
import br.com.gabrieudev.budget_bridge_backend.application.exceptions.InternalErrorException;
import br.com.gabrieudev.budget_bridge_backend.application.exceptions.NotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleUserNotFoundException(NotFoundException e) {
        ApiResponseDTO<String> apiResponse = ApiResponseDTO.error(e.getMessage(), 404);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        ApiResponseDTO<String> apiResponse = ApiResponseDTO.error(e.getMessage(), 500);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ApiResponseDTO<String> apiResponse = ApiResponseDTO.error(Objects.requireNonNull(e.getFieldError()).getDefaultMessage(), 422);
        return new ResponseEntity<>(apiResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleBusinessRuleException(BusinessRuleException e) {
        ApiResponseDTO<String> apiResponse = ApiResponseDTO.error(e.getMessage(), 409);
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleInternalErrorException(InternalErrorException e) {
        ApiResponseDTO<String> apiResponse = ApiResponseDTO.error(e.getMessage(), 500);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}