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

        final Integer[] result = new Integer[1];

        Mono<Integer> mono = Mono.just(1).log()
                .doOnEach(integerSignal -> {
                    signals.add(integerSignal);
                    System.out.println("Signal... : " + integerSignal);
                });


        mono.subscribe(integer -> result[0] = integer);

        Assert.assertEquals(signals.size(), 2);
        Assert.assertEquals(signals.get(0).getType().name(),"ON_NEXT");
        Assert.assertEquals(signals.get(1).getType().name(),"ON_COMPLETE");
        Assert.assertEquals(result[0].intValue(), 1);
    }

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


    @Test
    public void test_mono_empty(){

        List<Signal<Integer>> signals = new ArrayList<>(4);
        Mono<Integer> mono = Mono.<Integer>empty()
                .doOnEach(signals::add);

        mono.subscribe();

        Assert.assertEquals(signals.size(), 1);
        Assert.assertTrue(signals.get(0).isOnComplete());
    }


    @Test
    public void test_mono_null(){

        Mono<String> result = Mono.empty();
        Assert.assertNull(result.block());

        /*
        String expected = null;
        Mono<String> result = Mono.empty();
        Assert.assertEquals(expected, result.block());
        */
    }


    



}
