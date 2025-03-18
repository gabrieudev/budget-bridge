package br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.ApiResponseDTO;
import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.category.CategoryDTO;
import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.category.CreateCategoryDTO;
import br.com.gabrieudev.budget_bridge_backend.application.services.CategoryService;
import br.com.gabrieudev.budget_bridge_backend.domain.entities.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@SecurityRequirement(name = "Keycloak")
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(
        summary = "Criar categoria",
        description = "Cria uma categoria",
        tags = {"Category"}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Categoria criada com sucesso"
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Já existe uma categoria com esse nome",
                content = @Content(
                    schema = @Schema(implementation = ApiResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno do servidor",
                content = @Content(
                    schema = @Schema(implementation = ApiResponseDTO.class)
                )
            ),
        }
    )
    @PostMapping
    public ResponseEntity<ApiResponseDTO<CategoryDTO>> create(
        @Valid
        @RequestBody
        CreateCategoryDTO createCategoryDTO,

        @AuthenticationPrincipal Jwt jwt
    ) {
        String userId = jwt.getSubject();

        Category createdCategory = categoryService.create(createCategoryDTO.toDomain(), userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.created(CategoryDTO.fromDomain(createdCategory)));
    }

    @Operation(
        summary = "Excluir categoria",
        description = "Exclui uma categoria pelo id",
        tags = {"Category"}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "204",
                description = "Categoria excluida com sucesso"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Não autorizado",
                content = @Content(
                    schema = @Schema(implementation = ApiResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno do servidor",
                content = @Content(
                    schema = @Schema(implementation = ApiResponseDTO.class)
                )
            ),
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<String>> delete(
        @Schema(
            description = "Identificador da categoria",
            example = "b8b8b8b8-b8b8-b8b8-b8b8-b8b8b8b8b8b8"
        )
        @PathVariable UUID id,

        @AuthenticationPrincipal Jwt jwt
    ) {
        String userId = jwt.getSubject();

        categoryService.delete(id, userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponseDTO.noContent("Categoria excluida com sucesso"));
    }

    @Operation(
        summary = "Listar categorias",
        description = "Lista todas as categorias",
        tags = {"Category"}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Categorias listadas com sucesso"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Não autorizado",
                content = @Content(
                    schema = @Schema(implementation = ApiResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno do servidor",
                content = @Content(
                    schema = @Schema(implementation = ApiResponseDTO.class)
                )
            ),
        }
    )
    @GetMapping
    public ResponseEntity<ApiResponseDTO<Page<CategoryDTO>>> getAll(
        @Schema(
            description = "Número da página",
            example = "0"
        )
        @RequestParam(required = true) Integer page,
        
        @Schema(
            description = "Tamanho da página",
            example = "10"
        )
        @RequestParam(required = true) Integer size,

        @AuthenticationPrincipal Jwt jwt
    ) {
        String userId = jwt.getSubject();

        List<CategoryDTO> categories = categoryService.findAll(userId, page, size)
            .stream()
            .map(CategoryDTO::fromDomain)
            .toList();

        Page<CategoryDTO> categoriesPage = new PageImpl<>(categories, PageRequest.of(page, size), categories.size());

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.ok(categoriesPage));
    }
}
