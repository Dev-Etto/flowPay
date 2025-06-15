package com.dev_etto.flowPay.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record TransactionRequestDTO(
        @NotNull(message = "O ID da conta de origem é obrigatório")
        Long sourceAccountId,

        @NotNull(message = "O ID da conta de destino é obrigatório")
        Long destinationAccountId,

        @NotNull(message = "O valor é obrigatório")
        @Positive(message = "O valor da transação deve ser positivo")
        BigDecimal amount
) {}