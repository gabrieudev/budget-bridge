package br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.controllers;

import java.time.LocalDateTime;
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
import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.transaction.CreateTransactionDTO;
import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.transaction.TransactionDTO;
import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.transaction.UpdateTransactionDTO;
import br.com.gabrieudev.budget_bridge_backend.application.services.TransactionService;
import br.com.gabrieudev.budget_bridge_backend.domain.entities.Transaction;
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
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(
        summary = "Criar transação",
        description = "Cria uma transação",
        tags = {"Transaction"}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Transação criada com sucesso"
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
    public ResponseEntity<ApiResponseDTO<TransactionDTO>> create(
        @Valid
        @RequestBody
        CreateTransactionDTO createTransactionDTO,

        @AuthenticationPrincipal Jwt jwt,

        HttpServletRequest request
    ) {
        log.info("POST /api/v1/transactions | Client: {}", request.getRemoteAddr());

        String userId = jwt.getSubject();

        Transaction createdTransaction = transactionService.create(createTransactionDTO.toDomain(), userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.created(TransactionDTO.fromDomain(createdTransaction)));
    }

    @Operation(
        summary = "Excluir transação",
        description = "Exclui uma transação",
        tags = {"Transaction"}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "204",
                description = "Transação excluida com sucesso"
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
            description = "Identificador da transação",
            example = "a3b4c5d6-e7f8-g9h0-i1j2-k3l4m5n6o7p8"
        )
        @PathVariable UUID id,

        @AuthenticationPrincipal Jwt jwt,

        HttpServletRequest request
    ) {
        log.info("DELETE /api/v1/transactions/{id} | Client: {}", request.getRemoteAddr());

        String userId = jwt.getSubject();

        transactionService.delete(id, userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponseDTO.noContent("Transação excluida com sucesso"));
    }

    @Operation(
        summary = "Listar transações",
        description = "Listar transações",
        tags = {"Transaction"}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Transações listadas com sucesso"
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
    public ResponseEntity<ApiResponseDTO<Page<TransactionDTO>>> getAll(
        @Schema(
            description = "Identificador da conta",
            example = "a3b4c5d6-e7f8-g9h0-i1j2-k3l4m5n6o7p8"
        )
        @RequestParam(required = false) UUID accountId,

        @Schema(
            description = "Identificador da categoria",
            example = "a3b4c5d6-e7f8-g9h0-i1j2-k3l4m5n6o7p8"
        )
        @RequestParam(required = false) String type,

        @Schema(
            description = "Identificador da categoria",
            example = "a3b4c5d6-e7f8-g9h0-i1j2-k3l4m5n6o7p8"
        )
        @RequestParam(required = false) UUID categoryId,

        @Schema(
            description = "Data de inicio",
            example = "2023-06-30T00:00:00"
        )
        @RequestParam(required = false) LocalDateTime startDate,

        @Schema(
            description = "Data de fim",
            example = "2023-06-30T00:00:00"
        )
        @RequestParam(required = false) LocalDateTime endDate,

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
        log.info("GET /api/v1/transactions | Client: {}", request.getRemoteAddr());

        String userId = jwt.getSubject();

        List<TransactionDTO> transactions = transactionService.findAll(userId, accountId, type, categoryId, startDate, endDate, page, size)
            .stream()
            .map(TransactionDTO::fromDomain)
            .toList();

        Page<TransactionDTO> transactionsPage = new PageImpl<>(transactions, PageRequest.of(page, size), transactions.size());

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.ok(transactionsPage));
    }

    @Operation(
        summary = "Obter transação",
        description = "Obter transação por id",
        tags = {"Transaction"}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Transação obtida com sucesso"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Não autorizado",
                content = @Content(
                    schema = @Schema(implementation = ApiResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Transação nao encontrada",
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
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<TransactionDTO>> getById(
        @Schema(
            description = "Identificador da transação",
            example = "a3b4c5d6-e7f8-g9h0-i1j2-k3l4m5n6o7p8"
        )
        @PathVariable UUID id,

        @AuthenticationPrincipal Jwt jwt,

        HttpServletRequest request
    ) {
        log.info("GET /api/v1/transactions/{id} | Client: {}", request.getRemoteAddr());

        String userId = jwt.getSubject();

        Transaction transaction = transactionService.findById(id, userId);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.ok(TransactionDTO.fromDomain(transaction)));
    }

    @Operation(
        summary = "Atualizar transação",
        description = "Atualiza uma transação pelo id",
        tags = {"Transaction"}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Transação atualizada com sucesso"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Não autorizado",
                content = @Content(
                    schema = @Schema(implementation = ApiResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Transação nao encontrada",
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
    public ResponseEntity<ApiResponseDTO<TransactionDTO>> update(
        @Schema(
            description = "Identificador da transação",
            example = "a3b4c5d6-e7f8-g9h0-i1j2-k3l4m5n6o7p8"
        )
        @PathVariable UUID id,

        @Valid
        @RequestBody
        UpdateTransactionDTO updateTransactionDTO,

        @AuthenticationPrincipal Jwt jwt,

        HttpServletRequest request
    ) {
        log.info("PUT /api/v1/transactions/{id} | Client: {}", request.getRemoteAddr());

        String userId = jwt.getSubject();

        Transaction updatedTransaction = transactionService.update(updateTransactionDTO.toDomain(), id, userId);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.ok(TransactionDTO.fromDomain(updatedTransaction)));
    }
}
