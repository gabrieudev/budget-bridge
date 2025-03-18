package br.com.gabrieudev.budget_bridge_backend.application.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.budget_bridge_backend.application.exceptions.BusinessRuleException;
import br.com.gabrieudev.budget_bridge_backend.application.exceptions.InternalErrorException;
import br.com.gabrieudev.budget_bridge_backend.application.exceptions.UnauthorizedException;
import br.com.gabrieudev.budget_bridge_backend.application.ports.input.GoalInputPort;
import br.com.gabrieudev.budget_bridge_backend.application.ports.output.AccountOutputPort;
import br.com.gabrieudev.budget_bridge_backend.application.ports.output.GoalOutputPort;
import br.com.gabrieudev.budget_bridge_backend.domain.entities.Account;
import br.com.gabrieudev.budget_bridge_backend.domain.entities.Goal;
import br.com.gabrieudev.budget_bridge_backend.domain.enums.GoalStatusEnum;

public class GoalService implements GoalInputPort {
    private final GoalOutputPort goalOutputPort;
    private final AccountOutputPort accountOutputPort;

    public GoalService(GoalOutputPort goalOutputPort, AccountOutputPort accountOutputPort) {
        this.goalOutputPort = goalOutputPort;
        this.accountOutputPort = accountOutputPort;
    }

    @Override
    public Goal create(Goal goal, String userId) {
        Account account = accountOutputPort.findById(goal.getAccount().getId())
                .orElseThrow(() -> new InternalErrorException("Conta não encontrada"));

        goal.setUserId(userId);
        goal.setCreatedAt(LocalDateTime.now());
        goal.setUpdatedAt(LocalDateTime.now());
        goal.setCurrentAmount(account.getBalance());
        goal.setStatus(GoalStatusEnum.PENDENTE);

        if (goal.getTargetAmount().compareTo(goal.getCurrentAmount()) < 0) {
            throw new BusinessRuleException("O valor alvo deve ser maior que o valor atual");
        }

        return goalOutputPort.create(goal)
                .orElseThrow(() -> new InternalErrorException("Erro ao criar meta"));
    }

    @Override
    public void delete(UUID id, String userId) {
        Goal goal = goalOutputPort.findById(id)
                .orElseThrow(() -> new InternalErrorException("Meta nao encontrada"));

        if (!goal.getUserId().equals(userId)) {
            throw new UnauthorizedException("Vocé nao pode deletar esta meta");
        }

        if (goal.getStatus().equals(GoalStatusEnum.CONCLUIDA)) {
            throw new BusinessRuleException("Meta já concluida");
        }

        if (!goalOutputPort.delete(id)) {
            throw new InternalErrorException("Erro ao deletar meta");
        }
    }

    @Override
    public void deposit(UUID id, String userId, BigDecimal amount) {
        Goal goal = goalOutputPort.findById(id)
                .orElseThrow(() -> new InternalErrorException("Meta não encontrada"));

        if (!goal.getUserId().equals(userId)) {
            throw new UnauthorizedException("Vocé não pode depositar nesta meta");
        }

        if (goal.getStatus().equals(GoalStatusEnum.CONCLUIDA)) {
            throw new BusinessRuleException("Meta já concluida");
        }

        if (amount.add(goal.getCurrentAmount()).compareTo(goal.getTargetAmount()) > 0) {
            throw new BusinessRuleException("O valor alvo deve ser maior que o valor atual");
        }

        if (amount.add(goal.getCurrentAmount()).compareTo(goal.getTargetAmount()) == 0) {
            goal.setStatus(GoalStatusEnum.CONCLUIDA);
        }

        goal.setCurrentAmount(goal.getCurrentAmount().add(amount));

        goalOutputPort.update(goal)
                .orElseThrow(() -> new InternalErrorException("Erro ao depositar na meta"));
    }

    @Override
    public List<Goal> findAll(String userId, UUID accountId, String type, String status, Integer page, Integer size) {
        return goalOutputPort.findAll(userId, accountId, type, status, page, size);
    }

    @Override
    public Double progressPercentage(UUID id, String userId) {
        Goal goal = goalOutputPort.findById(id)
                .orElseThrow(() -> new InternalErrorException("Meta não encontrada"));

        if (!goal.getUserId().equals(userId)) {
            throw new UnauthorizedException("Vocé não pode acessar esta meta");
        }

        return goalOutputPort.progressPercentage(id)
                .orElseThrow(() -> new InternalErrorException("Erro ao calcular progresso"));
    }

    @Override
    public Goal update(Goal goal, UUID goalId, String userId) {
        goal.setId(goalId);
        goal.setUpdatedAt(LocalDateTime.now());

        Goal goalToUpdate = goalOutputPort.findById(goalId)
                .orElseThrow(() -> new InternalErrorException("Meta não encontrada"));

        if (!goalToUpdate.getUserId().equals(userId)) {
            throw new UnauthorizedException("Vocé não pode atualizar esta meta");
        }

        if (goalToUpdate.getStatus().equals(GoalStatusEnum.CONCLUIDA)) {
            throw new BusinessRuleException("Meta já concluida");
        }

        return goalOutputPort.update(goal)
                .orElseThrow(() -> new InternalErrorException("Erro ao atualizar meta"));
    }
}
