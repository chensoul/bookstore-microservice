package com.chensoul.bookstore.user.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SwaggerConfig {

    @Bean
    OpenAPI openApi(SwaggerProperties properties) {
        Contact contact = new Contact()
                .name(properties.contact().name())
                .email(properties.contact().email());
        License license = new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0.html");
        Info info = new Info()
                .title(properties.title())
                .description(properties.description())
                .version(properties.version())
                .contact(contact)
                .license(license);
        Server server = new Server().url("http://localhost:8081").description("UserService Server URL");

        List<Tag> tags = List.of(new Tag().name("Products API").description("API endpoints to manage products"));
        return new OpenAPI()
                .info(info)
                .addSecurityItem(new SecurityRequirement().addList("Authorization"))
                .components(new Components().addSecuritySchemes("Bearer", createJwtTokenScheme()))
                .addServersItem(server)
                .tags(tags);
    }

    private SecurityScheme createJwtTokenScheme() {
        return new SecurityScheme()
                .name("Authorization")
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("Bearer");
    }
}
