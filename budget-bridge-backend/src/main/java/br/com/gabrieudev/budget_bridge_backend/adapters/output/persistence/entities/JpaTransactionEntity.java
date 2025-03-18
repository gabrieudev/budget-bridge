package br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.budget_bridge_backend.domain.entities.Transaction;
import br.com.gabrieudev.budget_bridge_backend.domain.enums.TransactionTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "transaction")
public class JpaTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private String userId;
    
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private JpaAccountEntity account;
    
    @ManyToOne
    @JoinColumn(name = "goal_id")
    private JpaGoalEntity goal;
    
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionTypeEnum type;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private JpaCategoryEntity category;
    
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static JpaTransactionEntity fromDomain(Transaction transaction) {
        return new ModelMapper().map(transaction, JpaTransactionEntity.class);
    }

    public void update(Transaction transaction) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(transaction, this);
    }

    public Transaction toDomain() {
        return new ModelMapper().map(this, Transaction.class);
    }
}
