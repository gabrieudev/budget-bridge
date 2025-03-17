package br.com.gabrieudev.budget_bridge_backend.application.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.budget_bridge_backend.application.exceptions.InternalErrorException;
import br.com.gabrieudev.budget_bridge_backend.application.exceptions.NotFoundException;
import br.com.gabrieudev.budget_bridge_backend.application.exceptions.UnauthorizedException;
import br.com.gabrieudev.budget_bridge_backend.application.ports.input.TransactionInputPort;
import br.com.gabrieudev.budget_bridge_backend.application.ports.output.AccountOutputPort;
import br.com.gabrieudev.budget_bridge_backend.application.ports.output.TransactionOutputPort;
import br.com.gabrieudev.budget_bridge_backend.domain.entities.Account;
import br.com.gabrieudev.budget_bridge_backend.domain.entities.Transaction;

public class TransactionService implements TransactionInputPort {
    private final TransactionOutputPort transactionOutputPort;
    private final AccountOutputPort accountOutputPort;

    public TransactionService(TransactionOutputPort transactionOutputPort, AccountOutputPort accountOutputPort) {
        this.transactionOutputPort = transactionOutputPort;
        this.accountOutputPort = accountOutputPort;
    }

    @Override
    public Transaction create(Transaction transaction, String userId) {
        transaction.setUserId(userId);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setUpdatedAt(LocalDateTime.now());

        Account account = accountOutputPort.findById(transaction.getAccount().getId())
                .orElseThrow(() -> new InternalErrorException("Account not found"));

        account.setBalance(account.getBalance().subtract(transaction.getAmount()));

        accountOutputPort.update(account)
                .orElseThrow(() -> new InternalErrorException("Error updating account"));

        return transactionOutputPort.create(transaction)
                .orElseThrow(() -> new InternalErrorException("Error creating transaction"));
    }

    @Override
    public void delete(UUID id, String userId) {
        Transaction transaction = transactionOutputPort.findById(id)
                .orElseThrow(() -> new InternalErrorException("Transaction not found"));

        if (!transaction.getUserId().equals(userId)) {
            throw new UnauthorizedException("You can't delete this transaction");
        }

        Account account = accountOutputPort.findById(transaction.getAccount().getId())
                .orElseThrow(() -> new InternalErrorException("Account not found"));

        account.setBalance(account.getBalance().add(transaction.getAmount()));

        accountOutputPort.update(account)
                .orElseThrow(() -> new InternalErrorException("Error updating account"));

        if (!transactionOutputPort.delete(id)) {
            throw new InternalErrorException("Error deleting transaction");
        }
    }

    @Override
    public List<Transaction> findAll(String userId, UUID accountId, String type, UUID categoryId,
            LocalDateTime startDate, LocalDateTime endDate, Integer page, Integer size) {
        return transactionOutputPort.findAll(userId, accountId, type, categoryId, startDate, endDate, page, size);
    }

    @Override
    public Transaction findById(UUID id, String userId) {
        Transaction transaction = transactionOutputPort.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction not found"));

        if (!transaction.getUserId().equals(userId)) {
            throw new UnauthorizedException("You can't access this transaction");
        }

        return transaction;
    }

    @Override
    public Transaction update(Transaction transaction, UUID transactionId, String userId) {
        Transaction transactionToUpdate = transactionOutputPort.findById(transactionId)
                .orElseThrow(() -> new NotFoundException("Transaction not found"));

        if (!transactionToUpdate.getUserId().equals(userId)) {
            throw new UnauthorizedException("You can't update this transaction");
        }

        transaction.setId(transactionId);
        transaction.setUpdatedAt(LocalDateTime.now());

        return transactionOutputPort.update(transaction)
                .orElseThrow(() -> new InternalErrorException("Error updating transaction"));
    }
}
