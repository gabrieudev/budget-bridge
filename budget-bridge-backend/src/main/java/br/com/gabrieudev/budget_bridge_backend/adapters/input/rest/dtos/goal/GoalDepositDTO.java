package br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.goal;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalDepositDTO {
    @Schema(
        description = "Valor depositado",
        example = "100.00"
    )
    @NotNull(message = "O valor depositado é obrigatório")
    private BigDecimal amount;
}
