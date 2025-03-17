package br.com.gabrieudev.budget_bridge_backend.application.ports.output;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.gabrieudev.budget_bridge_backend.domain.entities.Category;

public interface CategoryOutputPort {
    Optional<Category> create(Category category);

    List<Category> findAll(
            String userId,
            Integer page,
            Integer size);

    boolean delete(UUID id);

    boolean existsByUserIdAndName(String userId, String name);
}
