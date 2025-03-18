package br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.account;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.budget_bridge_backend.domain.entities.Account;
import br.com.gabrieudev.budget_bridge_backend.domain.enums.AccountCurrencyEnum;
import br.com.gabrieudev.budget_bridge_backend.domain.enums.AccountTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    @Schema(
        description = "Identificador único da conta",
        example = "f1a3b4c5-6d7e-8f9g-0a1b-2c3d4e5f6g7h"
    )
    private UUID id;
    
    @Schema(
        description = "Identificador único do usuário",
        example = "f1a3b4c5-6d7e-8f9g-0a1b-2c3d4e5f6g7h"
    )
    private String userId;
    
    @Schema(
        description = "Nome da conta",
        example = "Conta de crédito"
    )
    private String name;
    
    @Schema(
        description = "Tipo da conta",
        example = "CARTAO_DE_CREDITO"
    )
    private AccountTypeEnum type;
    
    @Schema(
        description = "Moeda da conta",
        example = "BRL"
    )
    private AccountCurrencyEnum currency;
    
    @Schema(
        description = "Saldo da conta",
        example = "1000.00"
    )
    private BigDecimal balance;
    
    @Schema(
        description = "Cor da conta",
        example = "#FF0000"
    )
    private String color;
    
    @Schema(
        description = "Se a conta está ativa",
        example = "true"
    )
    private Boolean isActive;
    
    @Schema(
        description = "Data de criação da conta",
        example = "2022-01-01T00:00:00"
    )
    private LocalDateTime createdAt;
    
    @Schema(
        description = "Data de atualização da conta",
        example = "2022-01-01T00:00:00"
    )
    private LocalDateTime updatedAt;

    public static AccountDTO fromDomain(Account account) {
        return new ModelMapper().map(account, AccountDTO.class);
    }

    public Account toDomain() {
        return new ModelMapper().map(this, Account.class);
    }
}
