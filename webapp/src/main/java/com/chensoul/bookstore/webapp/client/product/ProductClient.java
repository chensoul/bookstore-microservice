package com.chensoul.bookstore.webapp.client.product;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface ProductClient {

    @GetExchange("/product/api/products")
    PagedResult<Product> findProduct(Pageable pageable);

    @GetExchange("/product/api/products/{code}")
    ResponseEntity<Product> getProductByCode(@PathVariable String code);
}
