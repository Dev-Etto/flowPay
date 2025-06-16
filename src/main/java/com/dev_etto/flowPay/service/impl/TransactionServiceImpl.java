package com.dev_etto.flowPay.service.impl;

import com.dev_etto.flowPay.config.RabbitMQConfig;
import com.dev_etto.flowPay.controller.dto.TransactionRequestDTO;
import com.dev_etto.flowPay.controller.dto.TransactionResponseDTO;
import com.dev_etto.flowPay.controller.dto.TransactionStatusResponseDTO;
import com.dev_etto.flowPay.exception.AccountNotFoundException;
import com.dev_etto.flowPay.exception.InsufficientBalanceException;
import com.dev_etto.flowPay.model.Transaction;
import com.dev_etto.flowPay.model.TransactionStatus;
import com.dev_etto.flowPay.repository.AccountRepository;
import com.dev_etto.flowPay.repository.TransactionRepository;
import com.dev_etto.flowPay.service.TransactionService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final RabbitTemplate rabbitTemplate;

    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository, RabbitTemplate rabbitTemplate) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    @Transactional
    public TransactionResponseDTO createTransaction(TransactionRequestDTO request) {

        var sourceAccount = accountRepository.findById(request.sourceAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Conta de origem não encontrada"));
        
        accountRepository.findById(request.destinationAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Conta de destino não encontrada"));
        
        if (sourceAccount.getBalance().compareTo(request.amount()) < 0) {
            throw new InsufficientBalanceException("Saldo insuficiente na conta de origem");
        }
        
        Transaction newTransaction = new Transaction();
        newTransaction.setSourceAccountId(request.sourceAccountId());
        newTransaction.setDestinationAccountId(request.destinationAccountId());
        newTransaction.setAmount(request.amount());
        newTransaction.setStatus(TransactionStatus.PENDING);
        newTransaction.setCreatedAt(LocalDateTime.now());
        
        Transaction savedTransaction = transactionRepository.save(newTransaction);
        
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, savedTransaction.getId());
        
        return new TransactionResponseDTO(
                savedTransaction.getId(),
                savedTransaction.getStatus().toString(),
                savedTransaction.getCreatedAt(),
                "Transação recebida e está em processamento."
        );
    }
    
    @Override
    public TransactionStatusResponseDTO findTransactionById(UUID id) {
        var transactionOptional = transactionRepository.findById(id);
    
        return transactionOptional.map(trx -> new TransactionStatusResponseDTO(
                    trx.getId(),
                    trx.getStatus().toString(),
                    trx.getCreatedAt(),
                    trx.getProcessedAt()))
            .orElseThrow(() -> new RuntimeException("Transação não encontrada"));
    }
}