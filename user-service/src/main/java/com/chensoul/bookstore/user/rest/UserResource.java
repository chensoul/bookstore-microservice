package com.chensoul.bookstore.user.rest;

import com.chensoul.bookstore.user.domain.PagedResult;
import com.chensoul.bookstore.user.domain.User;
import com.chensoul.bookstore.user.domain.UserService;
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
@RequestMapping("/api/users")
class UserResource {
    private static final Logger log = LoggerFactory.getLogger(UserResource.class);
    private final UserService userService;

    UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    PagedResult<User> findUser(
            @Parameter(hidden = true) @SortDefault(sort = "id") @PageableDefault final Pageable pageable) {
        log.info("Fetching users for page: {}", pageable);
        return userService.findUser(pageable);
    }

    @GetMapping("/{name}")
    ResponseEntity<User> getByName(@PathVariable String name) {
        log.info("Fetching user for name: {}", name);
        return ResponseEntity.ok(userService.getByName(name));
    }
}
