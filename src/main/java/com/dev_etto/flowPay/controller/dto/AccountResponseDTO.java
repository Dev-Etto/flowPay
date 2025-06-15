package com.dev_etto.flowPay.controller.dto;

import java.math.BigDecimal;

public record AccountResponseDTO(Long id, String ownerName, String document, BigDecimal balance) {}