package com.example.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MonoTests {

    @Test
    public void test_mono_just(){

        List<Signal<Integer>> signals = new ArrayList<>(4);

        Mono<Integer> mono = Mono.just(1).log()
                /*
                //.doOnEach(signals::add);
                .doOnEach(new Consumer<Signal<Integer>>() {
                    @Override
                    public void accept(Signal<Integer> integerSignal) {
                        signals.add(integerSignal);
                        System.out.println("Signal... : " + integerSignal);
                    }
                });
                */
                .doOnEach(integerSignal -> {
                    signals.add(integerSignal);
                    System.out.println("Signal... : " + integerSignal);
                });

        mono.subscribe();


    }

}
