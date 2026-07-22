package com.fx.controller;

import com.fx.converter.IngredientByIdConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.reactive.server.WebTestClient;

// when use somithing like this be careful about shit beans that couldn`t creating
@WebFluxTest(
    controllers = TestController.class,
    properties = "spring.main.web-application-type=reactive",
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = IngredientByIdConverter.class
    )
)
public class TestAsyncControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testPageAsyncTest() {
        webTestClient.get()
            .uri("/test/test_page_button_async")
            .exchange()
            .expectStatus().isOk();
    }
}