package com.chensoul.bookstore.user.domain;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
        super();
    }

    public static UserNotFoundException forName(String name) {
        return new UserNotFoundException("User with name " + name + " not found");
    }
}
