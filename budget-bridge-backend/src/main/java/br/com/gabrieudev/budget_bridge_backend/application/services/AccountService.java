package br.com.gabrieudev.budget_bridge_backend.application.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.budget_bridge_backend.application.exceptions.BusinessRuleException;
import br.com.gabrieudev.budget_bridge_backend.application.exceptions.InternalErrorException;
import br.com.gabrieudev.budget_bridge_backend.application.exceptions.NotFoundException;
import br.com.gabrieudev.budget_bridge_backend.application.exceptions.UnauthorizedException;
import br.com.gabrieudev.budget_bridge_backend.application.ports.input.AccountInputPort;
import br.com.gabrieudev.budget_bridge_backend.application.ports.output.AccountOutputPort;
import br.com.gabrieudev.budget_bridge_backend.domain.entities.Account;

public class AccountService implements AccountInputPort {
    private final AccountOutputPort accountOutputPort;

    public AccountService(AccountOutputPort accountOutputPort) {
        this.accountOutputPort = accountOutputPort;
    }

    @Override
    public Account create(Account account, String userId) {
        account.setIsActive(true);
        account.setBalance(BigDecimal.ZERO);
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        account.setUserId(userId);

        if (accountOutputPort.existsByUserIdAndName(userId, account.getName())) {
            throw new BusinessRuleException("Já existe uma conta com esse nome");
        }

        return accountOutputPort.create(account)
                .orElseThrow(() -> new InternalErrorException("Erro ao criar conta"));
    }

    @Override
    public void delete(UUID id, String userId) {
        Account account = accountOutputPort.findById(id)
                .orElseThrow(() -> new InternalErrorException("Conta não encontrada"));

        if (!account.getUserId().equals(userId)) {
            throw new UnauthorizedException("Você não pode deletar esta conta");
        }

        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new BusinessRuleException("Conta possui saldo");
        }

        account.setIsActive(false);

        accountOutputPort.update(account)
                .orElseThrow(() -> new InternalErrorException("Erro ao deletar conta"));
    }

    @Override
    public List<Account> findAll(String userId, String name, String type, String currency, Integer page, Integer size) {
        return accountOutputPort.findAll(userId, name, type, currency, page, size);
    }

    @Override
    public Account findById(UUID id, String userId) {
        Account account = accountOutputPort.findById(id)
                .orElseThrow(() -> new NotFoundException("Conta não encontrada"));

        if (!account.getUserId().equals(userId)) {
            throw new UnauthorizedException("Você não pode acessar esta conta");
        }

        return account;
    }

    @Override
    public Account update(Account account, UUID accountId, String userId) {
        account.setId(accountId);
        account.setUpdatedAt(LocalDateTime.now());

        Account accountToUpdate = accountOutputPort.findById(accountId)
                .orElseThrow(() -> new InternalErrorException("Conta não encontrada"));

        if (!accountToUpdate.getUserId().equals(userId)) {
            throw new UnauthorizedException("Você não pode atualizar esta conta");
        }

        return accountOutputPort.update(account)
                .orElseThrow(() -> new InternalErrorException("Erro ao atualizar conta"));
    }
}

