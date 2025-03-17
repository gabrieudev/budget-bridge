package br.com.gabrieudev.budget_bridge_backend.application.ports.output;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.gabrieudev.budget_bridge_backend.domain.entities.Category;

public interface CategoryOutputPort {
    Optional<Category> create(Category category, String userId);

    List<Category> findAll(
            String userId,
            Integer page,
            Integer size);

    boolean delete(UUID id, String userId);
}
