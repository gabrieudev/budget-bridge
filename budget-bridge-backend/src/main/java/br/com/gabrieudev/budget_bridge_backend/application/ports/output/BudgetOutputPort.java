package br.com.gabrieudev.budget_bridge_backend.application.ports.output;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.gabrieudev.budget_bridge_backend.domain.entities.Budget;

public interface BudgetOutputPort {
    Optional<Budget> create(Budget budget);

    List<Budget> getCurrent(String userId, Integer page, Integer size);

    boolean delete(UUID id);

    Optional<Budget> findById(UUID id);
}
