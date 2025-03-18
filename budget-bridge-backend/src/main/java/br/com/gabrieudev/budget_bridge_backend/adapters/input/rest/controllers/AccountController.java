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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.ApiResponseDTO;
import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.account.AccountDTO;
import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.account.CreateAccountDTO;
import br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.account.UpdateAccountDTO;
import br.com.gabrieudev.budget_bridge_backend.application.services.AccountService;
import br.com.gabrieudev.budget_bridge_backend.domain.entities.Account;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin
@SecurityRequirement(name = "Keycloak")
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(
        summary = "Criar conta",
        description = "Cria uma nova conta",
        tags = {"Account"}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Conta criada com sucesso"
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Já existe uma conta com esse nome",
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
    public ResponseEntity<ApiResponseDTO<AccountDTO>> create(
        @Valid
        @RequestBody
        CreateAccountDTO createAccountDTO,

        @AuthenticationPrincipal Jwt jwt
    ) {
        String userId = jwt.getSubject();

        Account createdAccount = accountService.create(createAccountDTO.toDomain(), userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.created(AccountDTO.fromDomain(createdAccount)));
    }

    @Operation(
        summary = "Listar contas",
        description = "Lista todas as contas do usuário",
        tags = {"Account"}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Contas listadas com sucesso"
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
    public ResponseEntity<ApiResponseDTO<Page<AccountDTO>>> getAll(
        @Schema(
            description = "Nome da conta",
            example = "Conta de crédito"
        )
        @RequestParam(required = false) String name,
        
        @Schema(
            description = "Tipo da conta",
            example = "CARTAO_DE_CREDITO"
        )
        @RequestParam(required = false) String type,
        
        @Schema(
            description = "Moeda da conta",
            example = "BRL"
        )
        @RequestParam(required = false) String currency,
        
        @Schema(
            description = "Cor da conta",
            example = "#FF0000"
        )
        @RequestParam(required = false) String color,
        
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

        List<AccountDTO> accounts = accountService.findAll(userId, name, type, currency, page, size)
                .stream()
                .map(AccountDTO::fromDomain)
                .toList();

        Page<AccountDTO> accountsPage = new PageImpl<>(accounts, PageRequest.of(page, size), accounts.size());

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.ok(accountsPage));
    }

    @Operation(
        summary = "Buscar conta",
        description = "Busca uma conta pelo id",
        tags = {"Account"}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Conta encontrada com sucesso"
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
                description = "Conta não encontrada",
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
    public ResponseEntity<ApiResponseDTO<AccountDTO>> getById(
        @Schema(
            description = "Identificador da conta",
            example = "12345678-1234-1234-1234-123456789012"
        )
        @PathVariable UUID id,

        @AuthenticationPrincipal Jwt jwt
    ) {
        String userId = jwt.getSubject();

        Account account = accountService.findById(id, userId);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.ok(AccountDTO.fromDomain(account)));
    }

    @Operation(
        summary = "Atualizar conta",
        description = "Atualiza uma conta pelo id",
        tags = {"Account"}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Conta atualizada com sucesso"
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
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<AccountDTO>> update(
        @Schema(
            description = "Identificador da conta",
            example = "12345678-1234-1234-1234-123456789012"
        )
        @PathVariable UUID id,

        @Valid
        @RequestBody
        UpdateAccountDTO updateAccountDTO,

        @AuthenticationPrincipal Jwt jwt
    ) {
        String userId = jwt.getSubject();

        Account updatedAccount = accountService.update(updateAccountDTO.toDomain(), id, userId);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.ok(AccountDTO.fromDomain(updatedAccount)));
    }

    @Operation(
        summary = "Excluir conta",
        description = "Exclui uma conta pelo id",
        tags = {"Account"}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "204",
                description = "Conta excluida com sucesso"
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
            description = "Identificador da conta",
            example = "12345678-1234-1234-1234-123456789012"
        )
        @PathVariable UUID id,

        @AuthenticationPrincipal Jwt jwt
    ) {
        String userId = jwt.getSubject();

        accountService.delete(id, userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponseDTO.noContent("Conta excluída com sucesso"));
    }
}
