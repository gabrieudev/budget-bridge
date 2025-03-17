package br.com.gabrieudev.budget_bridge_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SecurityScheme(
		name = "Keycloak"
		, openIdConnectUrl = "${kc.base-url}/realms/${kc.realm}/.well-known/openid-configuration"
		, scheme = "bearer"
		, type = SecuritySchemeType.OPENIDCONNECT
		, in = SecuritySchemeIn.HEADER
)
@SpringBootApplication
public class BudgetBridgeBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgetBridgeBackendApplication.class, args);
	}

}
