package com.dev_etto.flowPay.service.impl;

import com.dev_etto.flowPay.controller.dto.AccountRequestDTO;
import com.dev_etto.flowPay.controller.dto.AccountResponseDTO;
import com.dev_etto.flowPay.model.Account;
import com.dev_etto.flowPay.repository.AccountRepository;
import com.dev_etto.flowPay.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
  
public AccountResponseDTO createAccount(AccountRequestDTO request) {
    Account newAccount = new Account(null, request.ownerName(), request.email(), request.initialBalance(), request.document());
    
    Account savedAccount = accountRepository.save(newAccount);
    
    return new AccountResponseDTO(savedAccount.getId(), savedAccount.getOwnerName(), savedAccount.getDocument(), savedAccount.getEmail(), savedAccount.getBalance());
}
}