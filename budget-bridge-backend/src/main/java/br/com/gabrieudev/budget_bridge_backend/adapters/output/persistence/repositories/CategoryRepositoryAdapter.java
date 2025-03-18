package br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.entities.JpaCategoryEntity;
import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.repositories.jpa.JpaCategoryRepository;
import br.com.gabrieudev.budget_bridge_backend.application.ports.output.CategoryOutputPort;
import br.com.gabrieudev.budget_bridge_backend.domain.entities.Category;

@Component
public class CategoryRepositoryAdapter implements CategoryOutputPort {
    private final JpaCategoryRepository jpaCategoryRepository;

    public CategoryRepositoryAdapter(JpaCategoryRepository jpaCategoryRepository) {
        this.jpaCategoryRepository = jpaCategoryRepository;
    }

    @Override
    public Optional<Category> create(Category category) {
        try {
            JpaCategoryEntity jpaCategoryEntity = JpaCategoryEntity.fromDomain(category);

            return Optional.of(jpaCategoryRepository.save(jpaCategoryEntity).toDomain());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(UUID id) {
        try {
            jpaCategoryRepository.deleteById(id);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean existsByUserIdAndName(String userId, String name) {
        return jpaCategoryRepository.existsByUserIdAndName(userId, name);
    }

    @Override
    public List<Category> findAll(String userId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return jpaCategoryRepository.findAllByCriteria(userId, pageable)
                .stream()
                .map(JpaCategoryEntity::toDomain)
                .toList();
    }
}
