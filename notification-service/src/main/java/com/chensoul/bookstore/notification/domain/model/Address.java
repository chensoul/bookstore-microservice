package com.chensoul.bookstore.notification.domain.model;

public record Address(
        String addressLine1, String addressLine2, String city, String state, String zipCode, String country) {}
