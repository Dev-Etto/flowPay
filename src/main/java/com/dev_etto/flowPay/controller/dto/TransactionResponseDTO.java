package com.dev_etto.flowPay.controller.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponseDTO(
        UUID transactionId,
        String status,
        LocalDateTime createdAt,
        String message
) {}