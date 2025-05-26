package com.chensoul.bookstore.product.rest;

import com.chensoul.bookstore.product.domain.PagedResult;
import com.chensoul.bookstore.product.domain.Product;
import com.chensoul.bookstore.product.domain.ProductNotFoundException;
import com.chensoul.bookstore.product.domain.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
class ProductResource {
    private static final Logger log = LoggerFactory.getLogger(ProductResource.class);
    private final ProductService productService;

    ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    PagedResult<Product> getProducts(
            @Parameter(hidden = true) @SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable) {
        log.info("Fetching products for page: {}", pageable);
        return productService.findProduct(pageable);
    }

    @GetMapping("/{code}")
    ResponseEntity<Product> getProductByCode(@PathVariable String code) {
        log.info("Fetching product for code: {}", code);
        return productService
                .getProductByCode(code)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> ProductNotFoundException.forCode(code));
    }
}
