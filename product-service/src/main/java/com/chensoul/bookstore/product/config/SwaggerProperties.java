package com.chensoul.bookstore.product.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "bookstore.openapi")
@Validated
public record SwaggerProperties(
        @DefaultValue("ProductService API") String title,
        @DefaultValue("ProductService API Swagger Documentation") String description,
        @DefaultValue("v1.0.0") String version,
        Contact contact) {

    public record Contact(@DefaultValue("chensoul") String name, @DefaultValue("ichensoul@gmall.com") String email) {}
}
