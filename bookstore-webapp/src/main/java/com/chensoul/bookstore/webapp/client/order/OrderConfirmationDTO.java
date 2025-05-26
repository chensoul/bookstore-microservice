package com.chensoul.bookstore.webapp.client.order;

public record OrderConfirmationDTO(String orderNumber, OrderStatus status) {}
