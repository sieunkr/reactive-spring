package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StepVerifierTests {

    @Test
    public void mono_just_test(){

        Mono<String> mono = Mono.just("에디킴");

        StepVerifier.create(mono)
                .expectNext("에디킴")
                .verifyComplete();

    }

    @Test
    public void flux_just_test(){

        Flux<String> source = Flux.just("에디킴", "아이유");
        StepVerifier.create(source)
                .expectNext("에디킴")
                .expectNext("아이유")
                .verifyComplete();
    }

    @Test
    public void flux_just_test_02(){

        Flux<String> source = Flux.just("에디킴", "아이유");
        StepVerifier.create(source)
                .expectNext("에디킴","아이유")
                .verifyComplete();
    }

    @Test
    public void mono_map_test(){

        Mono<String> monoMap = Mono.just("Eddy")
                .map(String::toLowerCase);

        StepVerifier.create(monoMap)
                .expectNext("eddy")
                .verifyComplete();

    }

    @Test
    public void flux_filter_test(){

        Flux<String> nameFlux = Flux.just("Eddy", "Irene")
                .filter(name -> name.toUpperCase().startsWith("I"))
                .map(String::toLowerCase);

        StepVerifier.create(nameFlux)
                .expectNext("irene")
                .verifyComplete();
    }










}
