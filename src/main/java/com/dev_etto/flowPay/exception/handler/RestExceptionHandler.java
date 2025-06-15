package com.dev_etto.flowPay.exception.handler;

import com.dev_etto.flowPay.controller.dto.ErrorResponseDTO;
import com.dev_etto.flowPay.exception.AccountNotFoundException;
import com.dev_etto.flowPay.exception.InsufficientBalanceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class RestExceptionHandler {
    
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccountNotFound(AccountNotFoundException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("CONTA_NAO_ENCONTRADA", ex.getMessage());
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponseDTO> handleInsufficientBalance(InsufficientBalanceException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("SALDO_INSUFICIENTE", ex.getMessage());
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

        @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationErrors(MethodArgumentNotValidException ex) {
        String errorMessage = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("DADOS_INVALIDOS", errorMessage);
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}