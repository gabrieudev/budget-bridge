package br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.budget;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.category.CategoryDTO;
import br.com.gabrieudev.budget_bridge_backend.domain.entities.Budget;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetDTO {
    @Schema(
        description = "Identificador único do orçamento",
        example = "f1a3b4c5-6d7e-8f9g-0a1b-2c3d4e5f6g7h"
    )
    private UUID id;
    
    @Schema(
        description = "Identificador único do usuário",
        example = "f1a3b4c5-6d7e-8f9g-0a1b-2c3d4e5f6g7h"
    )
    private String userId;
    
    private CategoryDTO category;
    
    @Schema(
        description = "Valor alvo do orçamento",
        example = "1000.00"
    )
    private BigDecimal targetAmount;
    
    @Schema(
        description = "Valor gasto no orçamento",
        example = "500.00"
    )
    private BigDecimal spentAmount;
    
    @Schema(
        description = "Data de início do orçamento",
        example = "2022-01-01T00:00:00"
    )
    private LocalDateTime startDate;
    
    @Schema(
        description = "Data de término do orçamento",
        example = "2022-12-31T23:59:59"
    )
    private LocalDateTime endDate;

    public static BudgetDTO fromDomain(Budget budget) {
        return new ModelMapper().map(budget, BudgetDTO.class);
    }

    public static Budget toDomain(BudgetDTO budgetDTO) {
        return new ModelMapper().map(budgetDTO, Budget.class);
    }
}
