package br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.transaction;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

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
public class UpdateTransactionDTO {
    @Schema(
        description = "Tipo da transação",
        example = "DESPESA"
    )
    @NotNull(message = "O tipo da transação é obrigatório")
    private TransactionTypeEnum type;
    
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
