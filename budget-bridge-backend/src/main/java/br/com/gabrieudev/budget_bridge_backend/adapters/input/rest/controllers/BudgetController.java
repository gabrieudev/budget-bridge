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
import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.budget.BudgetDTO;
import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.budget.CreateBudgetDTO;
import br.com.gabrieudev.budget_bridge_backend.application.services.BudgetService;
import br.com.gabrieudev.budget_bridge_backend.domain.entities.Budget;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
@SecurityRequirement(name = "Keycloak")
@RequestMapping("/budgets")
public class BudgetController {
    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @Operation(
        summary = "Criar orçamento",
        description = "Cria um orçamento",
        tags = {"Budget"}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Orçamento criado com sucesso"
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Já existe um orçamento com esse nome",
                content = @Content(
                    schema = @Schema(implementation = ApiResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "422",
                description = "Dados inválidos",
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
    public ResponseEntity<ApiResponseDTO<BudgetDTO>> create(
        @Valid
        @RequestBody
        CreateBudgetDTO createBudgetDTO,

        @AuthenticationPrincipal Jwt jwt,

        HttpServletRequest request
    ) {
        log.info("POST /api/v1/budgets | Client: {}", request.getRemoteAddr());

        String userId = jwt.getSubject();

        Budget createdBudget = budgetService.create(createBudgetDTO.toDomain(), userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.created(BudgetDTO.fromDomain(createdBudget)));
    }

    @Operation(
        summary = "Listar orçamentos",
        description = "Lista todos os orçamentos vigentes do usuário",
        tags = {"Budget"}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Orçamentos listados com sucesso"
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
    @GetMapping("/current")
    public ResponseEntity<ApiResponseDTO<Page<BudgetDTO>>> getCurrent(
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

        @AuthenticationPrincipal Jwt jwt,

        HttpServletRequest request
    ) {
        log.info("GET /api/v1/budgets/current | Client: {}", request.getRemoteAddr());

        String userId = jwt.getSubject();

        List<BudgetDTO> budgets = budgetService.getCurrent(userId, page, size)
            .stream()
            .map(BudgetDTO::fromDomain)
            .toList();

        Page<BudgetDTO> pageBudgets = new PageImpl<>(budgets, PageRequest.of(page, size), budgets.size());

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.ok(pageBudgets));
    }

    @Operation(
        summary = "Excluir orçamento",
        description = "Exclui um orçamento pelo id",
        tags = {"Budget"}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "204",
                description = "Orçamento excluido com sucesso"
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
            description = "Identificador do orçamento",
            example = "123e4567-e89b-12d3-a456-426655440000"
        )
        @PathVariable UUID id,

        @AuthenticationPrincipal Jwt jwt,

        HttpServletRequest request
    ) {
        log.info("DELETE /api/v1/budgets/{id} | Client: {}", request.getRemoteAddr());

        String userId = jwt.getSubject();

        budgetService.delete(id, userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponseDTO.noContent("Orçamento excluido com sucesso"));
    }
}
