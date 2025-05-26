package com.chensoul.bookstore.user.rest;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import com.chensoul.bookstore.user.AbstractIT;
import com.chensoul.bookstore.user.domain.User;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@Sql("/test-data.sql")
class UserResourceTest extends AbstractIT {

    @Test
    void shouldReturnProducts() {
        given().contentType(ContentType.JSON)
                .when()
                .get("/api/users")
                .then()
                .statusCode(200)
                .body("data", hasSize(1))
                .body("totalElements", is(1))
                .body("pageNumber", is(0))
                .body("totalPages", is(1))
                .body("isFirst", is(true))
                .body("isLast", is(true))
                .body("hasNext", is(false))
                .body("hasPrevious", is(false));
    }

    @Test
    void shouldGetUserByCode() {
        User user = given().contentType(ContentType.JSON)
                .when()
                .get("/api/users/{name}", "user")
                .then()
                .statusCode(200)
                .assertThat()
                .extract()
                .body()
                .as(User.class);

        assertThat(user.name()).isEqualTo("user");
    }

    @Test
    void shouldReturnNotFoundWhenUserNameNotExists() {
        String name = "invalid_name";
        given().contentType(ContentType.JSON)
                .when()
                .get("/api/users/{name}", name)
                .then()
                .statusCode(404)
                .body("status", is(404))
                .body("title", is("User Not Found"))
                .body("detail", is("User with name " + name + " not found"));
    }
}
