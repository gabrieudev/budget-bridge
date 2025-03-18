package br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.category;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.budget_bridge_backend.domain.entities.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    @Schema(
        description = "Identificador único da categoria",
        example = "f1a3b4c5-6d7e-8f9g-0a1b-2c3d4e5f6g7h"
    )
    private UUID id;
    
    @Schema(
        description = "Identificador único do usuário",
        example = "f1a3b4c5-6d7e-8f9g-0a1b-2c3d4e5f6g7h"
    )
    private String userId;
    
    @Schema(
        description = "Nome da categoria",
        example = "Alimentação"
    )
    private String name;
    
    @Schema(
        description = "Cor da categoria",
        example = "#FF0000"
    )
    private String color;

    public static CategoryDTO fromDomain(Category category) {
        return new ModelMapper().map(category, CategoryDTO.class);
    }

    public static Category toDomain(CategoryDTO categoryDTO) {
        return new ModelMapper().map(categoryDTO, Category.class);
    }
}
