package br.com.gabrieudev.budget_bridge_backend.application.ports.output;

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

    Optional<Goal> update(Goal goal);
    
    boolean delete(UUID id);

    Optional<Goal> findById(UUID id);
}
