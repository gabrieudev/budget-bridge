package br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.entities.JpaCategoryEntity;
import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.entities.JpaTransactionEntity;
import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.repositories.jpa.JpaCategoryRepository;
import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.repositories.jpa.JpaTransactionRepository;
import br.com.gabrieudev.budget_bridge_backend.application.ports.output.TransactionOutputPort;
import br.com.gabrieudev.budget_bridge_backend.domain.entities.Transaction;

@Component
public class TransactionRepositoryAdapter implements TransactionOutputPort {
    private final JpaTransactionRepository jpaTransactionRepository;
    private final JpaCategoryRepository jpaCategoryRepository;

    public TransactionRepositoryAdapter(JpaTransactionRepository jpaTransactionRepository,
            JpaCategoryRepository jpaCategoryRepository) {
        this.jpaTransactionRepository = jpaTransactionRepository;
        this.jpaCategoryRepository = jpaCategoryRepository;
    }

    @Override
    public Optional<Transaction> create(Transaction transaction) {
        try {
            JpaTransactionEntity jpaTransactionEntity = JpaTransactionEntity.fromDomain(transaction);

            return Optional.of(jpaTransactionRepository.save(jpaTransactionEntity).toDomain());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(UUID id) {
        try {
            jpaTransactionRepository.deleteById(id);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean existsByCategoryId(UUID categoryId) {
        try {
            JpaCategoryEntity jpaCategoryEntity = jpaCategoryRepository.findById(categoryId).get();

            if (jpaCategoryEntity == null) {
                return false;
            }

            return jpaTransactionRepository.existsByCategory(jpaCategoryEntity);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Transaction> findAll(String userId, UUID accountId, String type, UUID categoryId,
            LocalDateTime startDate, LocalDateTime endDate, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return jpaTransactionRepository.findAllByCriteria(userId, accountId, type, categoryId, startDate, endDate,
                pageable)
                .stream()
                .map(JpaTransactionEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Transaction> findById(UUID id) {
        return jpaTransactionRepository.findById(id)
                .map(JpaTransactionEntity::toDomain);
    }

    @Override
    public Optional<Transaction> update(Transaction transaction) {
        try {
            JpaTransactionEntity jpaTransactionEntity = jpaTransactionRepository.findById(transaction.getId())
                    .orElse(null);

            if (jpaTransactionEntity == null) {
                return Optional.empty();
            }

            jpaTransactionEntity.update(transaction);

            return Optional.of(jpaTransactionRepository.save(jpaTransactionEntity).toDomain());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
