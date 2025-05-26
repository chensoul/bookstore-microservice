package com.chensoul.bookstore.webapp.controller;

import com.chensoul.bookstore.webapp.client.product.PagedResult;
import com.chensoul.bookstore.webapp.client.product.Product;
import com.chensoul.bookstore.webapp.client.product.ProductClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductClient productService;

    ProductController(ProductClient productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    String showProductsPage(
            @SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable, Model model) {
        model.addAttribute("pageNo", pageable.getPageNumber());
        return "products";
    }

    @GetMapping("/api/products")
    @ResponseBody
    PagedResult<Product> products(@SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable) {
        log.info("Fetching products for page: {}", pageable);
        return productService.findProduct(pageable);
    }
}
