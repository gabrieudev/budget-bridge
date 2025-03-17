package br.com.gabrieudev.budget_bridge_backend.application.services;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.budget_bridge_backend.application.exceptions.BusinessRuleException;
import br.com.gabrieudev.budget_bridge_backend.application.exceptions.InternalErrorException;
import br.com.gabrieudev.budget_bridge_backend.application.ports.input.CategoryInputPort;
import br.com.gabrieudev.budget_bridge_backend.application.ports.output.CategoryOutputPort;
import br.com.gabrieudev.budget_bridge_backend.application.ports.output.TransactionOutputPort;
import br.com.gabrieudev.budget_bridge_backend.domain.entities.Category;

public class CategoryService implements CategoryInputPort {
    private final CategoryOutputPort categoryOutputPort;
    private final TransactionOutputPort transactionOutputPort;

    public CategoryService(CategoryOutputPort categoryOutputPort, TransactionOutputPort transactionOutputPort) {
        this.categoryOutputPort = categoryOutputPort;
        this.transactionOutputPort = transactionOutputPort;
    }

    @Override
    public Category create(Category category, String userId) {
        category.setUserId(userId);

        if (categoryOutputPort.existsByUserIdAndName(userId, category.getName())) {
            throw new BusinessRuleException("Categoria já existe");
        }

        return categoryOutputPort.create(category)
                .orElseThrow(() -> new InternalErrorException("Erro ao criar categoria"));
    }

    @Override
    public void delete(UUID id, String userId) {
        if (transactionOutputPort.existsByCategoryId(id)) {
            throw new BusinessRuleException("Categoria tem transações");
        }

        if (!categoryOutputPort.delete(id)) {
            throw new InternalErrorException("Erro ao deletar categoria");
        }
    }

    @Override
    public List<Category> findAll(String userId, Integer page, Integer size) {
        return categoryOutputPort.findAll(userId, page, size);
    }
}

