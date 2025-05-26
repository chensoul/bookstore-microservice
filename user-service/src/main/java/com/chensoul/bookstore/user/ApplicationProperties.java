package com.chensoul.bookstore.user;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "bookstore")
public record ApplicationProperties() {}
