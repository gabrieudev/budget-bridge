package br.com.gabrieudev.budget_bridge_backend.application.ports.output;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.gabrieudev.budget_bridge_backend.domain.entities.Transaction;

public interface TransactionOutputPort {
    Optional<Transaction> create(Transaction transaction, String userId);

    List<Transaction> findAll(
            String userId,
            UUID accountId,
            String type,
            UUID categoryId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Integer page,
            Integer size);

    Optional<Transaction> findById(UUID id, String userId);

    Optional<Transaction> update(Transaction transaction, UUID transactionId, String userId);

    boolean delete(UUID id, String userId);
}
