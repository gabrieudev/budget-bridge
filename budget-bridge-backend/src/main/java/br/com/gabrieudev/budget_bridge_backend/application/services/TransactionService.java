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
                .orElseThrow(() -> new InternalErrorException("Conta não encontrada"));

        account.setBalance(account.getBalance().subtract(transaction.getAmount()));

        accountOutputPort.update(account)
                .orElseThrow(() -> new InternalErrorException("Erro ao atualizar conta"));

        return transactionOutputPort.create(transaction)
                .orElseThrow(() -> new InternalErrorException("Erro ao criar transação"));
    }

    @Override
    public void delete(UUID id, String userId) {
        Transaction transaction = transactionOutputPort.findById(id)
                .orElseThrow(() -> new InternalErrorException("Transação não encontrada"));

        if (!transaction.getUserId().equals(userId)) {
            throw new UnauthorizedException("Você não pode deletar esta transação");
        }

        Account account = accountOutputPort.findById(transaction.getAccount().getId())
                .orElseThrow(() -> new InternalErrorException("Conta não encontrada"));

        account.setBalance(account.getBalance().add(transaction.getAmount()));

        accountOutputPort.update(account)
                .orElseThrow(() -> new InternalErrorException("Erro ao atualizar conta"));

        if (!transactionOutputPort.delete(id)) {
            throw new InternalErrorException("Erro ao deletar transação");
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
                .orElseThrow(() -> new NotFoundException("Transação não encontrada"));

        if (!transaction.getUserId().equals(userId)) {
            throw new UnauthorizedException("Você não pode acessar esta transação");
        }

        return transaction;
    }

    @Override
    public Transaction update(Transaction transaction, UUID transactionId, String userId) {
        Transaction transactionToUpdate = transactionOutputPort.findById(transactionId)
                .orElseThrow(() -> new NotFoundException("Transação não encontrada"));

        if (!transactionToUpdate.getUserId().equals(userId)) {
            throw new UnauthorizedException("Você não pode atualizar esta transação");
        }

        transaction.setId(transactionId);
        transaction.setUpdatedAt(LocalDateTime.now());

        return transactionOutputPort.update(transaction)
                .orElseThrow(() -> new InternalErrorException("Erro ao atualizar transação"));
    }
}

