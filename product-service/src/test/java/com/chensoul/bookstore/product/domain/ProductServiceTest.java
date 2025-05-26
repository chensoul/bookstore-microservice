package com.chensoul.bookstore.product.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.chensoul.bookstore.product.AbstractIT;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = {"/test-data.sql"})
public class ProductServiceTest extends AbstractIT {
    @Autowired
    private ProductService productService;

    @Test
    void testGetProductByCode() {
        Optional<Product> result = productService.getProductByCode("P100");
        assertThat(result).as("Product with code 'P100' should be present").isPresent();
        assertThat(result.get().code())
                .as("Product code does not match the expected value")
                .isEqualTo("P100");
    }

    @Test
    void testFindProduct() {
        List<Product> products = productService.findProduct(Pageable.ofSize(10)).data();
        assertThat(products).as("The product list should not be null").isNotNull();
    }
}
