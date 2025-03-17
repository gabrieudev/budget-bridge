package br.com.gabrieudev.budget_bridge_backend.application.ports.output;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.gabrieudev.budget_bridge_backend.domain.entities.Account;

public interface AccountOutputPort {
    Optional<Account> create(Account account, String userId);

    List<Account> findAll(
            String userId,
            String name,
            String type,
            String currency,
            Integer page,
            Integer size);

    Optional<Account> findById(UUID id, String userId);

    Optional<Account> update(Account account, UUID accountId, String userId);

    boolean delete(UUID id, String userId);
}
