package com.fx.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void testReqStream() {
        var tcp = rSocketBuilder.tcp("localhost", 7000);
        var monoTcp = tcp
            .route("request_stream")
            .data("Hello")
            .retrieveFlux(String.class);

        StepVerifier.create(monoTcp)
            .expectNext("Hello")
            .expectNext("Hello01")
            .expectNext("Hello02")
            .expectNext("Hello03")
            .verifyComplete();
    }

    @Test
    public void testChannel() {
        var tcp = rSocketBuilder.tcp("localhost", 7000);
        var monoTcp = tcp
            .route("channel")
            .data(Flux.fromIterable(List.of("Hello", "Aloha", "Benxi")))
            .retrieveFlux(String.class);

        StepVerifier.create(monoTcp)
            .expectNext("HelloHello")
            .expectNext("AlohaAloha")
            .expectNext("BenxiBenxi")
            .verifyComplete();
    }

    @Test
    public void testFireAndForget() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream testStream = new PrintStream(outputStream);
        PrintStream originalOut = System.out;

        try {
            System.setOut(testStream);

            var tcp = rSocketBuilder.tcp("localhost", 7000);

            StepVerifier.create(
                    tcp.route("fire_and_forget")
                        .data("Hello")
                        .send()
                )
                .verifyComplete();

            //because standart out bufferized
            testStream.flush();
            //because standart out sloooooooooooooooooooow
            Thread.sleep(50);

            assertTrue( outputStream.toString().contains("Hello out"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.setOut(originalOut);
        }
    }
}
