package com.fx.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.test.StepVerifier;

@SpringBootTest
public class TestRSocketController {

    @Autowired
    private RSocketRequester.Builder rSocketBuilder;

    @Test
    public void testReqResp() {
        var tcp = rSocketBuilder.tcp("localhost", 7000);
        var monoTcp = tcp
            .route("request_response")
            .data("Hello")
            .retrieveMono(String.class);

        StepVerifier.create(monoTcp)
            .expectNext("Hello and u RESP")
            .verifyComplete();
    }
}
