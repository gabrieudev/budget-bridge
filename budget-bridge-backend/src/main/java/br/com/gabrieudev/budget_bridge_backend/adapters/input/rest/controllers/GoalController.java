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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.ApiResponseDTO;
import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.goal.CreateGoalDTO;
import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.goal.GoalDTO;
import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.goal.GoalDepositDTO;
import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.goal.UpdateGoalDTO;
import br.com.gabrieudev.budget_bridge_backend.application.services.GoalService;
import br.com.gabrieudev.budget_bridge_backend.domain.entities.Goal;
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
@RequestMapping("/goals")
public class GoalController {
    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @Operation(
        summary = "Criar meta",
        description = "Cria uma meta",
        tags = {"Goal"}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Meta criada com sucesso"
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Valor alvo menor que valor atual",
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
    public ResponseEntity<ApiResponseDTO<GoalDTO>> create(
        @Valid
        @RequestBody
        CreateGoalDTO createGoalDTO,

        @AuthenticationPrincipal Jwt jwt
    ) {
        String userId = jwt.getSubject();

        Goal createdGoal = goalService.create(createGoalDTO.toDomain(), userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.created(GoalDTO.fromDomain(createdGoal)));
    }

    @Operation(
        summary = "Excluir meta",
        description = "Exclui uma meta pelo id",
        tags = {"Goal"}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "204",
                description = "Meta excluida com sucesso"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Não autorizado",
                content = @Content(
                    schema = @Schema(implementation = ApiResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Meta já concluida",
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
            description = "Identificador da meta",
            example = "a3b4c5d6-e7f8-g9h0-i1j2-k3l4m5n6o7p8"
        )
        @PathVariable UUID id,

        @AuthenticationPrincipal Jwt jwt
    ) {
        String userId = jwt.getSubject();

        goalService.delete(id, userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponseDTO.noContent("Meta excluida com sucesso"));
    }

    @Operation(
        summary = "Depositar na meta",
        description = "Deposita na meta pelo id",
        tags = {"Goal"}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "204",
                description = "Depósito realizado com sucesso"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Não autorizado",
                content = @Content(
                    schema = @Schema(implementation = ApiResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Valor alvo menor que valor atual",
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
    @PostMapping("/{id}/deposit")
    public ResponseEntity<ApiResponseDTO<String>> deposit(
        @Schema(
            description = "Identificador da meta",
            example = "a3b4c5d6-e7f8-g9h0-i1j2-k3l4m5n6o7p8"
        )
        @PathVariable UUID id,

        @Valid
        @RequestBody
        GoalDepositDTO goalDepositDTO,

        @AuthenticationPrincipal Jwt jwt
    ) {
        String userId = jwt.getSubject();

        goalService.deposit(id, userId, goalDepositDTO.getAmount());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponseDTO.noContent("Depósito realizado com sucesso"));
    }

    @Operation(
        summary = "Listar metas",
        description = "Listar metas",
        tags = {"Goal"}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Metas listadas com sucesso"
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
    public ResponseEntity<ApiResponseDTO<Page<GoalDTO>>> getAll(
        @Schema(
            description = "Identificador da conta",
            example = "a3b4c5d6-e7f8-g9h0-i1j2-k3l4m5n6o7p8"
        )
        @RequestParam(required = false) UUID accountId,

        @Schema(
            description = "Tipo da meta",
            example = "INVESTIMENTO"
        )
        @RequestParam(required = false) String type,

        @Schema(
            description = "Status da meta",
            example = "PENDENTE"
        )
        @RequestParam(required = false) String status,

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

        List<GoalDTO> goals = goalService.findAll(userId, accountId, type, status, page, size)
            .stream()
            .map(GoalDTO::fromDomain)
            .toList();

        Page<GoalDTO> pageGoals = new PageImpl<>(goals, PageRequest.of(page, size), goals.size());
        
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.ok(pageGoals));
    }

    @Operation(
        summary = "Atualizar meta",
        description = "Atualiza uma meta pelo id",
        tags = {"Goal"}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Meta atualizada com sucesso"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Não autorizado",
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
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<GoalDTO>> update(
        @Schema(
            description = "Identificador da meta",
            example = "a3b4c5d6-e7f8-g9h0-i1j2-k3l4m5n6o7p8"
        )
        @PathVariable UUID id,

        @Valid
        @RequestBody
        UpdateGoalDTO updateGoalDTO,

        @AuthenticationPrincipal Jwt jwt
    ) {
        String userId = jwt.getSubject();

        Goal updatedGoal = goalService.update(updateGoalDTO.toDomain(), id, userId);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.ok(GoalDTO.fromDomain(updatedGoal)));
    }

}
