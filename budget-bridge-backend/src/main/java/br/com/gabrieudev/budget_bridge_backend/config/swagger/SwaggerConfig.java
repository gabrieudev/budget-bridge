package br.com.gabrieudev.budget_bridge_backend.config.swagger;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
        @Value("${api.url}")
        private String apiUrl;

        @Bean
        OpenAPI budgetBridgeOpenAPI() {
                return new OpenAPI()
                                .info(new Info().title("Budget Bridge")
                                                .description("Interface Swagger para a API de Budget Bridge.")
                                                .version("v0.0.1")
                                                .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                                .externalDocs(new ExternalDocumentation()
                                                .description("Repositório GitHub")
                                                .url("https://github.com/gabrieudev/budget-bridge"))
                                .addServersItem(new Server().url(apiUrl).description("Servidor principal"));
        }
}
