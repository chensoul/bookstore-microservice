package com.chensoul.bookstore.user.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.chensoul.bookstore.user.AbstractIT;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = {"/test-data.sql"})
public class UserServiceTest extends AbstractIT {
    @Autowired
    private UserService userService;

    @Test
    void testGetUserByName() {
        User result = userService.getByName("user");
        assertThat(result).as("User with name 'user' should be present").isNotNull();
        assertThat(result.name())
                .as("User code does not match the expected value")
                .isEqualTo("user");
    }

    @Test
    void testFindUser() {
        List<User> users = userService.findUser(Pageable.ofSize(10)).data();
        assertThat(users).as("The product list should not be null").isNotNull();
    }
}
