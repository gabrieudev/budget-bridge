package br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.entities.JpaGoalEntity;
import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.repositories.jpa.JpaGoalRepository;
import br.com.gabrieudev.budget_bridge_backend.application.ports.output.GoalOutputPort;
import br.com.gabrieudev.budget_bridge_backend.domain.entities.Goal;

@Component
public class GoalRepositoryAdapter implements GoalOutputPort {
    private final JpaGoalRepository jpaGoalRepository;

    public GoalRepositoryAdapter(JpaGoalRepository jpaGoalRepository) {
        this.jpaGoalRepository = jpaGoalRepository;
    }

    @Override
    public Optional<Goal> create(Goal goal) {
        try {
            JpaGoalEntity jpaGoalEntity = JpaGoalEntity.fromDomain(goal);

            return Optional.of(jpaGoalRepository.save(jpaGoalEntity).toDomain());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(UUID id) {
        try {
            jpaGoalRepository.deleteById(id);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Goal> findAll(String userId, UUID accountId, String type, String status, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return jpaGoalRepository.findAllByCriteria(userId, accountId, type, status, pageable)
                .stream()
                .map(JpaGoalEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Goal> findById(UUID id) {
        return jpaGoalRepository.findById(id)
                .map(JpaGoalEntity::toDomain);
    }

    @Override
    public Optional<Goal> update(Goal goal) {
        try {
            JpaGoalEntity jpaGoalEntity = JpaGoalEntity.fromDomain(goal);

            jpaGoalEntity.update(goal);

            return Optional.of(jpaGoalRepository.save(jpaGoalEntity).toDomain());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
