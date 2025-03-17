package br.com.gabrieudev.budget_bridge_backend.application.services;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.budget_bridge_backend.application.exceptions.BusinessRuleException;
import br.com.gabrieudev.budget_bridge_backend.application.exceptions.InternalErrorException;
import br.com.gabrieudev.budget_bridge_backend.application.exceptions.UnauthorizedException;
import br.com.gabrieudev.budget_bridge_backend.application.ports.input.BudgetInputPort;
import br.com.gabrieudev.budget_bridge_backend.application.ports.output.BudgetOutputPort;
import br.com.gabrieudev.budget_bridge_backend.domain.entities.Budget;

public class BudgetService implements BudgetInputPort {
    private final BudgetOutputPort budgetOutputPort;

    public BudgetService(BudgetOutputPort budgetOutputPort) {
        this.budgetOutputPort = budgetOutputPort;
    }

    @Override
    public Budget create(Budget budget, String userId) {
        budget.setUserId(userId);

        if (budget.getEndDate().isBefore(budget.getStartDate())) {
            throw new BusinessRuleException("A data de início deve ser antes da data de término");
        }

        return budgetOutputPort.create(budget)
                .orElseThrow(() -> new InternalErrorException("Erro ao criar orçamento"));
    }

    @Override
    public List<Budget> current(String userId) {
        return budgetOutputPort.current(userId);
    }

    @Override
    public void delete(UUID id, String userId) {
        Budget budget = budgetOutputPort.findById(id)
                .orElseThrow(() -> new InternalErrorException("Orçamento não encontrado"));

        if (!budget.getUserId().equals(userId)) {
            throw new UnauthorizedException("Você não pode deletar este orçamento");
        }

        if (!budgetOutputPort.delete(id)) {
            throw new InternalErrorException("Erro ao deletar orçamento");
        }
    }

    @Override
    public Double progressPercentage(UUID id, String userId) {
        Budget budget = budgetOutputPort.findById(id)
                .orElseThrow(() -> new InternalErrorException("Orçamento não encontrado"));

        if (!budget.getUserId().equals(userId)) {
            throw new UnauthorizedException("Você não pode acessar este orçamento");
        }

        return budgetOutputPort.progressPercentage(id)
                .orElseThrow(() -> new InternalErrorException("Orçamento não encontrado"));
    }
}

