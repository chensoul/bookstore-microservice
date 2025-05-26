package com.chensoul.bookstore.user.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.chensoul.bookstore.user.TestcontainersConfiguration;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

// @DataJpaTest(
//        properties = {
//                "spring.test.database.replace=none",
//                "spring.datasource.url=jdbc:tc:postgresql:17-alpine:///db",
//        })
@DataJpaTest
@Import(TestcontainersConfiguration.class)
@Sql("/test-data.sql")
class UserRepositoryTest {

    @Autowired
    private UserRepository productRepository;

    // You don't need to test the methods provided by Spring Data JPA.
    // This test is to demonstrate how to write tests for the repository layer.
    @Test
    void shouldGetAllProducts() {
        List<UserEntity> products = productRepository.findAll();
        assertThat(products).hasSize(1);
    }

    @Test
    void shouldGetProductByCode() {
        UserEntity product = productRepository.getByNameIgnoreCase("user").orElseThrow();
        assertThat(product.getName()).isEqualTo("user");
    }

    @Test
    void shouldReturnEmptyWhenProductCodeNotExists() {
        assertThat(productRepository.getByNameIgnoreCase("invalid_name")).isEmpty();
    }
}
