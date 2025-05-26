package com.chensoul.bookstore.user.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan("com.chensoul.bookstore.user.domain")
@EnableJpaRepositories("com.chensoul.bookstore.user.domain")
@EnableTransactionManagement
public class DomainConfig {}
