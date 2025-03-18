package br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.entities.JpaAccountEntity;
import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.repositories.jpa.JpaAccountRepository;
import br.com.gabrieudev.budget_bridge_backend.application.ports.output.AccountOutputPort;
import br.com.gabrieudev.budget_bridge_backend.domain.entities.Account;

@Component
public class AccountRepositoryAdapter implements AccountOutputPort {
    private final JpaAccountRepository jpaAccountRepository;

    public AccountRepositoryAdapter(JpaAccountRepository jpaAccountRepository) {
        this.jpaAccountRepository = jpaAccountRepository;
    }

    @Override
    public Optional<Account> create(Account account) {
        try {
            JpaAccountEntity jpaAccountEntity = JpaAccountEntity.fromDomain(account);

            return Optional.of(jpaAccountRepository.save(jpaAccountEntity).toDomain());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsByUserIdAndName(String userId, String name) {
        return jpaAccountRepository.existsByUserIdAndName(userId, name);
    }

    @Override
    public List<Account> findAll(String userId, String name, String type, String currency, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return jpaAccountRepository.findAllByCriteria(userId, name, type, currency, pageable)
                .stream()
                .map(JpaAccountEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Account> findById(UUID id) {
        return jpaAccountRepository.findById(id)
                .map(JpaAccountEntity::toDomain);
    }

    @Override
    public Optional<Account> update(Account account) {
        try {
            JpaAccountEntity jpaAccountEntity = JpaAccountEntity.fromDomain(account);

            jpaAccountEntity.update(account);

            return Optional.of(jpaAccountRepository.save(jpaAccountEntity).toDomain());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
