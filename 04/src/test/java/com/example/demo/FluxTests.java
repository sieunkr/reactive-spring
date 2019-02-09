package com.example.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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


    @Test
    public void test_flux_lazy(){
        //TODO: 테스트 코드를 어떻게 짜야할지 모르겠다.
        Flux<Integer> flux = Flux.range(1,9)
                .flatMap(n -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return Mono.just(3 * n);
                }).log();

        System.out.println("아직 구독 안한 상황... 데이터 전달을 하지 않는다.");

        flux.subscribe(value -> {
                    System.out.println(value);
                },
                null,
                () -> {
                    System.out.println("데이터 수신 완료");
                });

        System.out.println("전체 완료...");

    }


}

