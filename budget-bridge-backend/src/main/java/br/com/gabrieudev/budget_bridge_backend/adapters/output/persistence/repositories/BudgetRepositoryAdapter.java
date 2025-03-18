package br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.entities.JpaBudgetEntity;
import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.repositories.jpa.JpaBudgetRepository;
import br.com.gabrieudev.budget_bridge_backend.application.ports.output.BudgetOutputPort;
import br.com.gabrieudev.budget_bridge_backend.domain.entities.Budget;

@Component
public class BudgetRepositoryAdapter implements BudgetOutputPort {
    private final JpaBudgetRepository jpaBudgetRepository;

    public BudgetRepositoryAdapter(JpaBudgetRepository jpaBudgetRepository) {
        this.jpaBudgetRepository = jpaBudgetRepository;
    }

    @Override
    public Optional<Budget> create(Budget budget) {
        try {
            JpaBudgetEntity jpaBudgetEntity = JpaBudgetEntity.fromDomain(budget);

            return Optional.of(jpaBudgetRepository.save(jpaBudgetEntity).toDomain());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(UUID id) {
        try {
            jpaBudgetRepository.deleteById(id);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Optional<Budget> findById(UUID id) {
        return jpaBudgetRepository.findById(id)
                .map(JpaBudgetEntity::toDomain);
    }

    @Override
    public List<Budget> getCurrent(String userId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return jpaBudgetRepository.findCurrent(userId, LocalDateTime.now(), pageable)
                .stream()
                .map(JpaBudgetEntity::toDomain)
                .toList();
    }
}
