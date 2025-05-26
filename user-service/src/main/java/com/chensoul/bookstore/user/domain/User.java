package com.chensoul.bookstore.user.domain;

public record User(String name, String password, String role, String openid, String loginType) {}
