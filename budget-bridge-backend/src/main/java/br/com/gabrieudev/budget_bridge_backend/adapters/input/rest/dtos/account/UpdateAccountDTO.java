package br.com.gabrieudev.budget_bridge_backend.adapters.input.rest.dtos.account;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.budget_bridge_backend.domain.entities.Account;
import br.com.gabrieudev.budget_bridge_backend.domain.enums.AccountCurrencyEnum;
import br.com.gabrieudev.budget_bridge_backend.domain.enums.AccountTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountDTO {
    @Schema(
        description = "Nome da conta",
        example = "Conta de crédito"
    )
    @NotBlank(message = "O nome da conta é obrigatório")
    private String name;
    
    @Schema(
        description = "Tipo da conta",
        example = "CARTAO_DE_CREDITO"
    )
    @NotNull(message = "O tipo da conta é obrigatório")
    private AccountTypeEnum type;
    
    @Schema(
        description = "Moeda da conta",
        example = "BRL"
    )
    @NotNull(message = "A moeda da conta é obrigatória")
    private AccountCurrencyEnum currency;
    
    @Schema(
        description = "Cor da conta",
        example = "#FF0000"
    )
    @NotBlank(message = "A cor da conta é obrigatória")
    private String color;

    public Account toDomain() {
        return new ModelMapper().map(this, Account.class);
    }
}
