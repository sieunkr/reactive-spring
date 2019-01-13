package com.example.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FluxTests {

    /*
    @Test
    public void contextLoads() {
    }
    */

    @Test
    public void test_flux_just(){

        List<String> names = new ArrayList<>();

        Flux<String> flux = Flux.just("에디킴", "아이린").log();
        flux.subscribe(names::add);

        Assert.assertEquals(names, Arrays.asList("에디킴", "아이린"));

    }

    @Test
    public void test_flux_just_consumer(){

        List<String> names = new ArrayList<>();

        Flux<String> flux = Flux.just("에디킴", "아이린").log();
        /*
        flux.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) {

            }
        });
        */
        flux.subscribe(s -> {
            System.out.println("시퀀스 수신 : " + s);
            names.add(s);
        });

        Assert.assertEquals(names, Arrays.asList("에디킴", "아이린"));

    }


    @Test
    public void test_just_just_consumer_02(){

        List<String> names = new ArrayList<>();

        Flux<String> flux = Flux.just("에디킴", "아이린").log();
        flux.subscribe(names::add);

        Assert.assertNotEquals(names, Arrays.asList("에디킴"));
    }

    @Test
    public void test_flux_fromArray(){

        List<String> names = new ArrayList<>();

        Flux<String> flux = Flux.fromArray(new String[]{"에디킴", "아이유", "아이린"}).log();
        flux.subscribe(names::add);

        Assert.assertEquals(names, Arrays.asList("에디킴", "아이유", "아이린"));

    }


    @Test
    public void test_flux_fromIterable(){

        List<String> names = new ArrayList<>();

        Flux<String> flux = Flux.fromIterable(Arrays.asList("아이유", "아이린")).log();
        flux.subscribe(names::add);

        Assert.assertEquals(names, Arrays.asList("아이유", "아이린"));

    }

    @Test
    public void test_flux_empty(){

        List<String> names = new ArrayList<>();

        Flux<String> flux = Flux.empty();
        flux.subscribe(names::add);

        Assert.assertEquals(names.size(), 0);

    }


    @Test
    public void test_flux_subscriber(){

        Flux<String> flux = Flux.just("에디킴", "아이린").log();
        flux.subscribe(new MySubscriber());

        //TODO: 테스트 코드
    }




}

