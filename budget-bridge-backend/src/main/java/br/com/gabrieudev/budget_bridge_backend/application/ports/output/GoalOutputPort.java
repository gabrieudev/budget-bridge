package br.com.gabrieudev.budget_bridge_backend.application.ports.output;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.gabrieudev.budget_bridge_backend.domain.entities.Goal;

public interface GoalOutputPort {
    Optional<Goal> create(Goal goal);

    List<Goal> findAll(
            String userId,
            UUID accountId,
            String type,
            String status,
            Integer page,
            Integer size);

    boolean deposit(UUID id, BigDecimal amount);

    Optional<Double> progressPercentage(UUID id);

    Optional<Goal> update(Goal goal, UUID goalId);
    
    boolean delete(UUID id);
}
