package br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.goal;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.budget_bridge_backend.domain.entities.Goal;
import br.com.gabrieudev.budget_bridge_backend.domain.enums.GoalTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGoalDTO {
    @Schema(
        description = "Título da meta",
        example = "Viagem de férias"
    )
    @NotBlank(message = "O título da meta é obrigatório")
    private String title;
    
    @Schema(
        description = "Descrição da meta",
        example = "Viajar para a Europa"
    )
    private String description;
    
    @Schema(
        description = "Tipo da meta",
        example = "LAZER"
    )
    @NotNull(message = "O tipo da meta é obrigatório")
    private GoalTypeEnum type;
    
    @Schema(
        description = "Data de término da meta",
        example = "2023-06-30T00:00:00"
    )
    @NotNull(message = "A data de término da meta é obrigatória")
    @Future(message = "A data de término da meta deve ser futura")
    private LocalDateTime deadline;

    public static Goal toDomain(GoalDTO goalDTO) {
        return new ModelMapper().map(goalDTO, Goal.class);
    }
}
