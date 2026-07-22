package com.fx.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class RSocketController {

    @MessageMapping("request_response")
    public Mono<String> requestResponseController(Mono<String> request) {
        return request
            .map(req -> String.format("%s %s", req, "and u RESP"));
    }
}
