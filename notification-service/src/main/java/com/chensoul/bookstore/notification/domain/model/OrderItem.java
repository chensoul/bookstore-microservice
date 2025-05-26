package com.chensoul.bookstore.notification.domain.model;

import java.math.BigDecimal;

public record OrderItem(String code, String name, BigDecimal price, Integer quantity) {}
