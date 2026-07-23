package com.fx.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

//ATTENTION default using TCP, u can switch to WEBSOCKET for browser support and use same
// server(tomcat, netty) like http controllers(less vulnerability, less ports), moreover u need WEBSOCKET if u use firewall
@Controller
public class RSocketController {

    @MessageMapping("request_response")
    public Mono<String> requestResponseController(String request) {
        return Mono.just(request)
            .map(req -> String.format("%s %s", req, "and u RESP"));
    }

    @MessageMapping("request_stream")
    public Flux<String> requestStreamController(String request) {
        return Flux.fromIterable(List.of(request, request + "01", request + "02", request + "03"))
            .delayElements(Duration.ofMillis(200));
    }

    @MessageMapping("channel")
    public Flux<String> channelController(Flux<String> request) {
        return request
            .map(req -> String.format("%s%s", req, req));
    }

    @MessageMapping("fire_and_forget")
    public Mono<Void> fireAndForgetController(String request) {
        return Mono.just(request)
            .doOnNext(req -> System.out.printf("%s out", req))
            .thenEmpty(Mono.empty());
    }
}
