package br.com.gabrieudev.budget_bridge_backend.application.ports.output;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.gabrieudev.budget_bridge_backend.domain.entities.Budget;

public interface BudgetOutputPort {
    Optional<Budget> create(Budget budget, String userId);

    List<Budget> current(String userId);

    Optional<Double> progressPercentage(UUID id, String userId);

    boolean delete(UUID id, String userId);
}
