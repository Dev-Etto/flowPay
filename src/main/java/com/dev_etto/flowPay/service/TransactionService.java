package com.dev_etto.flowPay.service;

import com.dev_etto.flowPay.controller.dto.TransactionRequestDTO;
import com.dev_etto.flowPay.controller.dto.TransactionResponseDTO;
import com.dev_etto.flowPay.controller.dto.TransactionStatusResponseDTO;

import java.util.UUID;

public interface TransactionService {

    TransactionResponseDTO createTransaction(TransactionRequestDTO request);
    
    TransactionStatusResponseDTO findTransactionById(UUID id);
}