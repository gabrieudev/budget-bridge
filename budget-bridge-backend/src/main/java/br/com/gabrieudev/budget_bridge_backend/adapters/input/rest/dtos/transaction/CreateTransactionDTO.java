package br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.account.AccountDTO;
import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.category.CategoryDTO;
import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.goal.GoalDTO;
import br.com.gabrieudev.budget_bridge_backend.domain.entities.Transaction;
import br.com.gabrieudev.budget_bridge_backend.domain.enums.TransactionTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionDTO {
    @NotNull(message = "A conta da transação é obrigatória")
    private AccountDTO account;
    
    private GoalDTO goal;
    
    @Schema(
        description = "Tipo da transação",
        example = "DESPESA"
    )
    @NotNull(message = "O tipo da transação é obrigatório")
    private TransactionTypeEnum type;
    
    @NotNull(message = "A categoria da transação é obrigatória")
    private CategoryDTO category;
    
    @Schema(
        description = "Valor da transação",
        example = "100.00"
    )
    @NotNull(message = "O valor da transação é obrigatório")
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
    @NotNull(message = "A data da transação é obrigatória")
    private LocalDateTime transactionDate;

    public Transaction toDomain() {
        return new ModelMapper().map(this, Transaction.class);
    }
}
