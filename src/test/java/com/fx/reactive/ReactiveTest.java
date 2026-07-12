package com.fx.reactive;


import lombok.Value;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

@Execution(CONCURRENT)
public class ReactiveTest {

    @Test
    public void fluxRange_test() {
        Flux<Integer> fluxRange = Flux.range(0, 5);

        StepVerifier.create(fluxRange)
            .expectNext(0)
            .expectNext(1)
            .expectNext(2)
            .expectNext(3)
            .expectNext(4)
            .verifyComplete();
    }

    @Test
    public void fluxFromIterable_test() {
        List<String> listForFlux = new ArrayList<>();

        listForFlux.add("ONE");
        listForFlux.add("TWO");

        Flux<String> fluxIterable = Flux.fromIterable(listForFlux);

        StepVerifier.create(fluxIterable)
            .expectNext("ONE")
            .expectNext("TWO")
            .verifyComplete();
    }

    @Test
    public void fluxFromInterval_test() {
        Flux<Long> fluxInterval = Flux.interval(Duration.ofSeconds(1)).take(5L);

        StepVerifier.create(fluxInterval)
            .expectNext(0L)
            .expectNext(1L)
            .expectNext(2L)
            .expectNext(3L)
            .expectNext(4L)
            .verifyComplete();
    }

    @Test
    public void fluxZip_test() {
        Flux<Integer> fluxZipInteger = Flux.just(1, 2, 3);
        Flux<String> fluxZipString = Flux.just("One", "Two", "Three");

        Flux<Tuple2<Integer, String>> zippedFlux = Flux.zip(fluxZipInteger, fluxZipString);

        StepVerifier
            .create(zippedFlux)
            .expectNextMatches(p ->
                p.getT1().equals(1) && p.getT2().equals("One")
            )
            .expectNextMatches(p ->
                p.getT1().equals(2) && p.getT2().equals("Two")
            )
            .expectNextMatches(p ->
                p.getT1().equals(3) && p.getT2().equals("Three")
            )
            .verifyComplete();
    }

    @Test
    public void fluxMergeWith_test() {
        Flux<String> fluxZipInteger = Flux.just("1", "2", "3").delayElements(Duration.ofMillis(500L));
        Flux<String> fluxZipString = Flux.just("One", "Two", "Three").delaySubscription(Duration.ofMillis(100L))
            .delayElements(Duration.ofMillis(500L));

        Flux<String> mergeWithFlux = fluxZipInteger.mergeWith(fluxZipString);

        StepVerifier.create(mergeWithFlux)
            .expectNext("1")
            .expectNext("One")
            .expectNext("2")
            .expectNext("Two")
            .expectNext("3")
            .expectNext("Three")
            .verifyComplete();
    }

    @Test
    public void fluxFirstWithSignal_test() {
        Flux<String> fastFlux = Flux.just("Fast");
        Flux<String> slowFlux = Flux.just("Slow").delaySubscription(Duration.ofMillis(1L));

        Flux<String> mergeWithFlux = Flux.firstWithSignal(fastFlux, slowFlux);

        StepVerifier.create(mergeWithFlux)
            .expectNext("Fast")
            .verifyComplete();
    }

    @Test
    public void fluxSkip_test() {
        Flux<String> fastFlux = Flux.just("Fast", "Slow", "Crazy", "Megabomba", "Kilo").skip(4);

        StepVerifier.create(fastFlux)
            .expectNext("Kilo")
            .expectComplete()
            .verify(); // or should use just verifyComplete()
    }

    @Test
    public void fluxSkipTime_test() {
        Flux<String> fastFlux = Flux.just("Fast", "Slow", "Crazy", "Megabomba", "Kilo")
            .delayElements(Duration.ofSeconds(1))
            .skip(Duration.ofSeconds(4));

        StepVerifier.create(fastFlux)
            .expectNext("Megabomba")
            .expectNext("Kilo")
            .verifyComplete(); // or should use just verifyComplete()
    }

    @Test
    public void fluxTake_test() {
        Flux<String> fastFlux = Flux.just("Fast", "Slow", "Crazy", "Megabomba", "Kilo").take(2);

        StepVerifier.create(fastFlux)
            .expectNext("Fast")
            .expectNext("Slow")
            .verifyComplete();
    }

    @Test
    public void fluxTakeTime_test() {
        Flux<String> fastFlux = Flux.just("Fast", "Slow", "Crazy", "Megabomba", "Kilo")
            .delayElements(Duration.ofSeconds(1))
            .take(Duration.ofMillis(3500));

        StepVerifier.create(fastFlux)
            .expectNext("Fast")
            .expectNext("Slow")
            .expectNext("Crazy")
            .verifyComplete();
    }

    @Test
    public void fluxFilter_test() {
        Flux<String> fastFlux = Flux.just("Fast", "Slow", "Crazy", "Megabomba", "Kilo").filter(s -> s.length() < 5);

        StepVerifier.create(fastFlux)
            .expectNext("Fast")
            .expectNext("Slow")
            .expectNext("Kilo")
            .verifyComplete();
    }

    @Test
    public void fluxDistinct_test() {
        Flux<String> fastFlux = Flux.just("Fast", "Fast", "Slow").distinct();

        StepVerifier.create(fastFlux)
            .expectNext("Fast")
            .expectNext("Slow")
            .verifyComplete();
    }

    @Value
    static class TestClass {
        private String name;
        private String secondName;
    }

    @Test
    public void fluxMap_test() {
        Flux<TestClass> fastFlux = Flux.just("First Second", "Third Fourth").map(s -> {
            var args = s.split(" ");
            return new TestClass(args[0], args[1]);
        });

        StepVerifier.create(fastFlux)
            .expectNext(new TestClass("First", "Second"))
            .expectNext(new TestClass("Third", "Fourth"))
            .verifyComplete();
    }

    @Test
    public void fluxFlatMap_test() {
        var expectedList = Arrays.asList(
            new TestClass("First", "Second"),
            new TestClass("Third", "Fourth")
        );
        //difference between map and flatMap that
        // flux -> map -> element
        // flux -> flatMap -> flux AND I can configure strategy parallel execution(parallel, boundedElastic, singe, ...)
        Flux<TestClass> fastFlux = Flux.just("First Second", "Third Fourth").flatMap(s -> {
            var args = s.split(" ");
            var fluxBuf = new TestClass(args[0], args[1]);
            return Flux.just(fluxBuf);
        }).subscribeOn(Schedulers.boundedElastic());

        StepVerifier.create(fastFlux)
            .expectNextMatches(expectedList::contains)
            .expectNextMatches(expectedList::contains)
            .verifyComplete();
    }

    @Test
    public void fluxBuffer_test() { //if u wanna just one big buffer use collectList()
        Flux<String> fastFlux = Flux.just("Fast", "Slow", "Crazy", "Megabomba", "Kilo");
        Flux<List<String>> fastBufferedFlux = fastFlux.buffer(3); //difference types

        StepVerifier.create(fastBufferedFlux)
            .expectNext(Arrays.asList("Fast", "Slow", "Crazy"))
            .expectNext(Arrays.asList("Megabomba", "Kilo"))
            .verifyComplete();
    }

    @Test
    public void fluxAnyAll_test() {
        Flux<String> fastFlux = Flux.just("Fast", "Slow", "Crazy", "Megabomba", "Kilo");

        var fluxAll = fastFlux.all(w -> w.contains("a") || w.contains("l"));

        var fluxAny = fastFlux.any(w -> w.contains("q"));

        StepVerifier.create(fluxAll).expectNext(true).verifyComplete();

        StepVerifier.create(fluxAny).expectNext(false).verifyComplete();
    }
}
