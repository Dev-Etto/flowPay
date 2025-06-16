package com.dev_etto.flowPay.service;

import com.dev_etto.flowPay.controller.dto.AccountRequestDTO;
import com.dev_etto.flowPay.controller.dto.AccountResponseDTO;

public interface AccountService {

    AccountResponseDTO createAccount(AccountRequestDTO accountRequestDTO);

}