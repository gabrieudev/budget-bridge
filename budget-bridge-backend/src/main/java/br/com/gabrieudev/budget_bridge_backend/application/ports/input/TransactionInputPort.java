package br.com.gabrieudev.budget_bridge_backend.application.ports.input;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.budget_bridge_backend.domain.entities.Transaction;

public interface TransactionInputPort {
    Transaction create(Transaction transaction, String userId);

    List<Transaction> findAll(
            String userId,
            UUID accountId,
            String type,
            UUID categoryId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Integer page,
            Integer size);

    Transaction findById(UUID id, String userId);

    Transaction update(Transaction transaction, UUID transactionId, String userId);

    void delete(UUID id, String userId);
}
