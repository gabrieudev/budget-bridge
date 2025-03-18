package br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.budget;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.category.CategoryDTO;
import br.com.gabrieudev.budget_bridge_backend.domain.entities.Budget;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBudgetDTO {
    @NotNull(message = "A categoria do orçamento é obrigatória")
    private CategoryDTO category;
    
    @Schema(
        description = "Valor alvo do orçamento",
        example = "1000.00"
    )
    @NotNull(message = "O valor alvo do orçamento é obrigatório")
    @Positive(message = "O valor alvo do orçamento deve ser maior que zero")
    private BigDecimal targetAmount;
    
    @Schema(
        description = "Valor gasto no orçamento",
        example = "500.00"
    )
    @NotNull(message = "O valor gasto no orçamento é obrigatório")
    @Positive(message = "O valor gasto no orçamento deve ser maior que zero")
    private BigDecimal spentAmount;
    
    @Schema(
        description = "Data de início do orçamento",
        example = "2022-01-01T00:00:00"
    )
    @NotNull(message = "A data de início do orçamento é obrigatória")
    private LocalDateTime startDate;
    
    @Schema(
        description = "Data de término do orçamento",
        example = "2022-12-31T23:59:59"
    )
    @NotNull(message = "A data de término do orçamento é obrigatória")
    private LocalDateTime endDate;

    public static Budget toDomain(BudgetDTO budgetDTO) {
        return new ModelMapper().map(budgetDTO, Budget.class);
    }
}
