package br.com.gabrieudev.budget_bridge_backend.application.ports.input;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.budget_bridge_backend.domain.entities.Account;

public interface AccountInputPort {
    Account create(Account account, String userId);

    List<Account> findAll(
            String userId,
            String name,
            String type,
            String currency,
            Integer page,
            Integer size);

    Account findById(UUID id, String userId);

    Account update(Account account, UUID accountId, String userId);

    void delete(UUID id, String userId);
}
