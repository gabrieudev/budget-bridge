package br.com.gabrieudev.budget_bridge_backend.application.ports.input;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.budget_bridge_backend.domain.entities.Category;

public interface CategoryInputPort {
    Category create(Category category, String userId);

    List<Category> findAll(
            String userId,
            Integer page,
            Integer size);

    void delete(UUID id, String userId);
}
