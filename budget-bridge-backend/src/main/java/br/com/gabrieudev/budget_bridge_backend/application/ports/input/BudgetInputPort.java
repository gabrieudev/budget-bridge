package br.com.gabrieudev.budget_bridge_backend.application.ports.input;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.budget_bridge_backend.domain.entities.Budget;

public interface BudgetInputPort {
    Budget create(Budget budget, String userId);

    List<Budget> current(String userId);

    Double progressPercentage(UUID id, String userId);

    void delete(UUID id, String userId);
}
