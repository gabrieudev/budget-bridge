package br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.goal;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.account.AccountDTO;
import br.com.gabrieudev.budget_bridge_backend.domain.entities.Goal;
import br.com.gabrieudev.budget_bridge_backend.domain.enums.GoalStatusEnum;
import br.com.gabrieudev.budget_bridge_backend.domain.enums.GoalTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalDTO {
    @Schema(
        description = "Identificador único da meta",
        example = "f1a3b4c5-6d7e-8f9g-0a1b-2c3d4e5f6g7h"
    )
    private UUID id;
    
    @Schema(
        description = "Identificador único do usuário",
        example = "f1a3b4c5-6d7e-8f9g-0a1b-2c3d4e5f6g7h"
    )
    private String userId;
    
    private AccountDTO account;
    
    @Schema(
        description = "Título da meta",
        example = "Viagem de férias"
    )
    private String title;
    
    @Schema(
        description = "Descrição da meta",
        example = "Viajar para a Europa"
    )
    private String description;
    
    @Schema(
        description = "Valor alvo da meta",
        example = "1000.00"
    )
    private BigDecimal targetAmount;
    
    @Schema(
        description = "Valor atual da meta",
        example = "500.00"
    )
    private BigDecimal currentAmount;
    
    @Schema(
        description = "Tipo da meta",
        example = "LAZER"
    )
    private GoalTypeEnum type;
    
    @Schema(
        description = "Data de término da meta",
        example = "2023-06-30T00:00:00"
    )
    private LocalDateTime deadline;
    
    @Schema(
        description = "Status da meta",
        example = "PENDENTE"
    )
    private GoalStatusEnum status;
    
    @Schema(
        description = "Data de criação da meta",
        example = "2023-06-01T10:00:00"
    )
    private LocalDateTime createdAt;
    
    @Schema(
        description = "Data de atualização da meta",
        example = "2023-06-02T15:30:00"
    )
    private LocalDateTime updatedAt;

    public static GoalDTO fromDomain(Goal goal) {
        return new ModelMapper().map(goal, GoalDTO.class);
    }

    public static Goal toDomain(GoalDTO goalDTO) {
        return new ModelMapper().map(goalDTO, Goal.class);
    }
}
