package com.dev_etto.flowPay.controller;

import com.dev_etto.flowPay.controller.dto.TransactionRequestDTO;
import com.dev_etto.flowPay.controller.dto.TransactionResponseDTO;
import com.dev_etto.flowPay.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
@Tag(name = "Transactions", description = "Endpoints para gerenciamento de transações")
public class TransactionController {

    private final TransactionService transactionService;
    
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @Operation(summary = "Cria uma nova transação financeira")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Transação aceita e em processamento"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos ou saldo insuficiente"),
            @ApiResponse(responseCode = "404", description = "Conta de origem ou destino não encontrada")
    })
    public ResponseEntity<TransactionResponseDTO> createTransaction(
            @RequestBody @Valid TransactionRequestDTO requestDTO) {
        
        TransactionResponseDTO response = transactionService.createTransaction(requestDTO);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}