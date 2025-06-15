package com.dev_etto.flowPay.service;

import com.dev_etto.flowPay.controller.dto.TransactionRequestDTO;
import com.dev_etto.flowPay.controller.dto.TransactionResponseDTO;

public interface TransactionService {

    TransactionResponseDTO createTransaction(TransactionRequestDTO request);
}