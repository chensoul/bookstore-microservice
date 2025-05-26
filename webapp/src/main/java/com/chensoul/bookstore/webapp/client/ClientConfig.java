package com.chensoul.bookstore.webapp.client;

import com.chensoul.bookstore.webapp.ApplicationProperties;
import com.chensoul.bookstore.webapp.client.order.OrderServiceClient;
import com.chensoul.bookstore.webapp.client.product.ProductClient;
import java.time.Duration;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpRequestValues;
import org.springframework.web.service.invoker.HttpServiceArgumentResolver;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
class ClientConfig {
    private final ApplicationProperties properties;

    ClientConfig(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Bean
    RestClientCustomizer restClientCustomizer() {
        return restClientBuilder -> restClientBuilder
                .baseUrl(properties.apiGatewayUrl())
                .requestFactory(ClientHttpRequestFactoryBuilder.detect()
                        .build(ClientHttpRequestFactorySettings.defaults()
                                .withConnectTimeout(Duration.ofSeconds(5))
                                .withReadTimeout(Duration.ofSeconds(5))));
    }

    @Bean
    ProductClient productServiceClient(RestClient.Builder builder) {
        RestClient restClient = builder.build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .customArgumentResolver(new PageableArgumentResolver()) // 注册自定义解析器
                .build();
        return factory.createClient(ProductClient.class);
    }

    @Bean
    OrderServiceClient orderServiceClient(RestClient.Builder builder) {
        RestClient restClient = builder.build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build();
        return factory.createClient(OrderServiceClient.class);
    }

    public class PageableArgumentResolver implements HttpServiceArgumentResolver {
        @Override
        public boolean resolve(Object argument, MethodParameter parameter, HttpRequestValues.Builder requestValues) {
            if (parameter.getParameterType().equals(Pageable.class)) {
                Pageable pageable = (Pageable) argument;
                requestValues.addRequestParameter("page", String.valueOf(pageable.getPageNumber()));
                requestValues.addRequestParameter("size", String.valueOf(pageable.getPageSize()));

                if (pageable.getSort().isSorted()) {
                    for (Sort.Order order : pageable.getSort()) {
                        String direction = order.getDirection().name().toLowerCase();
                        String property = order.getProperty();
                        requestValues.addRequestParameter("sort", property + "," + direction);
                    }
                }
                return true;
            }
            return false;
        }
    }
}
