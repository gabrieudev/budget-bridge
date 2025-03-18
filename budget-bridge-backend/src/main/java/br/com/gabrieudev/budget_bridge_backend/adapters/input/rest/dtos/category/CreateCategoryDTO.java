package br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.category;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.budget_bridge_backend.domain.entities.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCategoryDTO {
    @Schema(
        description = "Nome da categoria",
        example = "Alimentação"
    )
    @NotBlank(message = "O nome da categoria é obrigatório")
    private String name;
    
    @Schema(
        description = "Cor da categoria",
        example = "#FF0000"
    )
    @NotBlank(message = "A cor da categoria é obrigatória")
    private String color;

    public Category toDomain() {
        return new ModelMapper().map(CreateCategoryDTO.class, Category.class);
    }
}
