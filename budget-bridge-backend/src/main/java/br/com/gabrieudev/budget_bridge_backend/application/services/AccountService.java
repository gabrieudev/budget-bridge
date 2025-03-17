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
        account.setActive(true);
        account.setBalance(BigDecimal.ZERO);
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        account.setUserId(userId);

        return accountOutputPort.create(account)
                .orElseThrow(() -> new InternalErrorException("Error creating account"));
    }

    @Override
    public void delete(UUID id, String userId) {
        Account account = accountOutputPort.findById(id)
                .orElseThrow(() -> new InternalErrorException("Account not found"));

        if (!account.getUserId().equals(userId)) {
            throw new UnauthorizedException("You can't delete this account");
        }

        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new BusinessRuleException("Account has balance");
        }

        account.setActive(false);

        accountOutputPort.update(account)
                .orElseThrow(() -> new InternalErrorException("Error deleting account"));
    }

    @Override
    public List<Account> findAll(String userId, String name, String type, String currency, Integer page, Integer size) {
        return accountOutputPort.findAll(userId, name, type, currency, page, size);
    }

    @Override
    public Account findById(UUID id, String userId) {
        Account account = accountOutputPort.findById(id)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        if (!account.getUserId().equals(userId)) {
            throw new UnauthorizedException("You can't access this account");
        }

        return account;
    }

    @Override
    public Account update(Account account, UUID accountId, String userId) {
        account.setId(accountId);
        account.setUpdatedAt(LocalDateTime.now());

        Account accountToUpdate = accountOutputPort.findById(accountId)
                .orElseThrow(() -> new InternalErrorException("Account not found"));

        if (!accountToUpdate.getUserId().equals(userId)) {
            throw new UnauthorizedException("You can't update this account");
        }

        return accountOutputPort.update(account)
                .orElseThrow(() -> new InternalErrorException("Error updating account"));
    }
}
