package com.dev_etto.flowPay.controller.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountResponseDTO(
    Long id,
    String ownerName,
    String email,
    String document, BigDecimal balance) {}