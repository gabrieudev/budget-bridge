package br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.account.AccountDTO;
import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.category.CategoryDTO;
import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.goal.GoalDTO;
import br.com.gabrieudev.budget_bridge_backend.domain.entities.Transaction;
import br.com.gabrieudev.budget_bridge_backend.domain.enums.TransactionTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    @Schema(
        description = "Identificador único da transação",
        example = "f1a3b4c5-6d7e-8f9g-0a1b-2c3d4e5f6g7h"
    )
    private UUID id;
    
    @Schema(
        description = "Identificador único do usuário",
        example = "f1a3b4c5-6d7e-8f9g-0a1b-2c3d4e5f6g7h"
    )
    private String userId;
    
    private AccountDTO account;
    
    private GoalDTO goal;
    
    @Schema(
        description = "Tipo da transação",
        example = "DESPESA"
    )
    private TransactionTypeEnum type;
    
    private CategoryDTO category;
    
    @Schema(
        description = "Valor da transação",
        example = "100.00"
    )
    private BigDecimal amount;
    
    @Schema(
        description = "Descrição da transação",
        example = "Aluguel"
    )
    private String description;
    
    @Schema(
        description = "Data da transação",
        example = "2023-06-30T00:00:00"
    )
    private LocalDateTime transactionDate;
    
    @Schema(
        description = "Data de criação da transação",
        example = "2023-06-30T00:00:00"
    )
    private LocalDateTime createdAt;
    
    @Schema(
        description = "Data de atualização da transação",
        example = "2023-06-30T00:00:00"
    )
    private LocalDateTime updatedAt;

    public static TransactionDTO fromDomain(Transaction transaction) {
        return new ModelMapper().map(transaction, TransactionDTO.class);
    }

    public Transaction toDomain() {
        return new ModelMapper().map(this, Transaction.class);
    }
}
