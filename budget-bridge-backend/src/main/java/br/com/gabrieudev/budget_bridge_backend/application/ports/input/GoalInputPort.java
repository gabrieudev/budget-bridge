package br.com.gabrieudev.budget_bridge_backend.application.ports.input;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.budget_bridge_backend.domain.entities.Goal;

public interface GoalInputPort {
    Goal create(Goal goal, String userId);

    List<Goal> findAll(
            String userId,
            UUID accountId,
            String type,
            String status,
            Integer page,
            Integer size);

    void deposit(UUID id, String userId, BigDecimal amount);

    Goal update(Goal goal, UUID goalId, String userId);
    
    void delete(UUID id, String userId);
}
