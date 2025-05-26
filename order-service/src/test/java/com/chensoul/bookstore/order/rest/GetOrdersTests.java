package com.chensoul.bookstore.order.rest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.chensoul.bookstore.order.AbstractIT;
import com.chensoul.bookstore.order.WithMockOAuth2User;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

class GetOrdersTests extends AbstractIT {

    @Test
    @WithMockOAuth2User(username = "user")
    void shouldGetOrdersSuccessfully() throws Exception {
        MvcTestResult result = mvc.perform(get("/api/orders"));
        assertThat(result).hasStatus(HttpStatus.OK);
    }
}
