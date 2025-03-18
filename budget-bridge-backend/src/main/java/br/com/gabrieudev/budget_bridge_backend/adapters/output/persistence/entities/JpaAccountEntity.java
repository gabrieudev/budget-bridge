package br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.budget_bridge_backend.domain.entities.Account;
import br.com.gabrieudev.budget_bridge_backend.domain.enums.AccountCurrencyEnum;
import br.com.gabrieudev.budget_bridge_backend.domain.enums.AccountTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "account")
public class JpaAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountTypeEnum type;

    @Column(name = "currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountCurrencyEnum currency;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static JpaAccountEntity fromDomain(Account account) {
        return new ModelMapper().map(account, JpaAccountEntity.class);
    }

    public void update(Account account) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(account, this);
    }

    public Account toDomain() {
        return new ModelMapper().map(this, Account.class);
    }
}
