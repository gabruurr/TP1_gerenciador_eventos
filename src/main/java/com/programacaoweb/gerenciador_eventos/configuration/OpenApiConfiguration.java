package com.programacaoweb.gerenciador_eventos.configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API gerenciador de eventos",
                version = "v1",
                contact = @Contact(
                        name = "Pedro Gabryel",
                        email = "pedronascimento2005@aluno.uespi.br"
                )
        )
)
public class OpenApiConfiguration {
}