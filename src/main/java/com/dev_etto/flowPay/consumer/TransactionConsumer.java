package com.dev_etto.flowPay.consumer;

import com.dev_etto.flowPay.config.RabbitMQConfig;
import com.dev_etto.flowPay.model.TransactionStatus;
import com.dev_etto.flowPay.repository.AccountRepository;
import com.dev_etto.flowPay.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TransactionConsumer {
  private static final Logger log = LoggerFactory.getLogger(TransactionConsumer.class);
  
  private final AccountRepository accountRepository;
  private final TransactionRepository transactionRepository;

  public TransactionConsumer(AccountRepository accountRepository, TransactionRepository transactionRepository) {
    this.accountRepository = accountRepository;
    this.transactionRepository = transactionRepository;
  }
  
  @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
  @Transactional
    public void processTransaction(UUID transactionId) {
        log.info("Processando transação recebida da fila: {}", transactionId);
        
        transactionRepository.findById(transactionId).ifPresentOrElse(transaction -> {
            
            var sourceAccount = accountRepository.findById(transaction.getSourceAccountId()).orElseThrow();
            var destinationAccount = accountRepository.findById(transaction.getDestinationAccountId()).orElseThrow();

            sourceAccount.setBalance(sourceAccount.getBalance().subtract(transaction.getAmount()));
            destinationAccount.setBalance(destinationAccount.getBalance().add(transaction.getAmount()));

            transaction.setStatus(TransactionStatus.COMPLETED);
            transaction.setProcessedAt(LocalDateTime.now());

            log.info("Transação {} processada e finalizada com sucesso!", transactionId);

        }, () -> log.error("Transação com ID {} não encontrada no banco de dados.", transactionId));
    }
}