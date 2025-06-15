package com.dev_etto.flowPay.controller.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record AccountRequestDTO(
        @NotBlank(message = "O nome do titular é obrigatório")
        String ownerName,
        @NotBlank(message = "O documento é obrigatório")
        String document,
        @NotNull(message = "O saldo inicial é obrigatório")
        @DecimalMin(value = "0.0", inclusive = true, message = "O saldo inicial não pode ser negativo")
        BigDecimal initialBalance
) {}