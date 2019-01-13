package com.example.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Signal;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FluxSubscriberTests {


    @Test
    public void test_flux_subscribe() {

        List<String> names = new ArrayList<>();

        Flux<String> flux = Flux.just("에디킴", "아이린").log();
        flux.subscribe(names::add);

        Assert.assertEquals(names, Arrays.asList("에디킴", "아이린"));

    }


    @Test
    public void test_flux_subscribe_complete() {

        List<String> names = new ArrayList<>();

        List<Signal<String>> signals = new ArrayList<>(10);

        Flux<String> flux = Flux.just("에디킴", "아이린", "아이유", "수지")
                .log()
                .doOnEach(signals::add);

        flux.subscribe(names::add,
                error -> {
                },
                () -> {
                    Assert.assertEquals(names, Arrays.asList("에디킴", "아이린", "아이유", "수지"));
                    Assert.assertEquals(signals.size(), 5);
                    Assert.assertFalse(signals.get(3).isOnComplete());
                    Assert.assertTrue(signals.get(3).isOnNext());
                    Assert.assertTrue(signals.get(4).isOnComplete());
                });

    }

    @Test
    public void test_flux_custom_subscriber() {

        List<Integer> integerList = new ArrayList<>();

        Flux.just(1, 2, 3, 4)
                .log()
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        integerList.add(integer);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        //TODO: request(1) 인 경우, Complete 가 되지 않은 상황에서 테스트가 통과하는것은 잘못된 테스트인듯.. 검토해보자.
                        //TODO: 결국 Reactor 에서 제공하는 StepVerifier 와 같은 Test 코드를 사용하는게 좋을 듯
                        Assert.assertEquals(integerList.size(), 4);
                    }
                });

    }


    @Test
    public void test_flux_custom_subscriber_not_complete() {





    }







}