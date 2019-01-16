package com.example.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Signal;
import reactor.core.publisher.SynchronousSink;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateTests {

    //https://github.com/reactor/reactor-core/blob/master/reactor-core/src/test/java/reactor/core/publisher/FluxCreateTest.java

    @Test
    public void test(){

        Flux<Integer> source = Flux.<Signal<Integer>>create(e -> {
            e.next(Signal.next(1));
            e.next(Signal.next(2));
            e.next(Signal.next(3));
            e.next(Signal.complete());
            System.out.println(e.isCancelled());
            System.out.println(e.requestedFromDownstream());
        }).dematerialize();

        List<Integer> list = new ArrayList<>();
        source.subscribe(list::add);

        List<Integer> expected = Arrays.asList(1, 2, 3);

        Assert.assertEquals(list.size(), 3);
        Assert.assertTrue(list.equals(expected));
    }

    @Test
    public void fluxCreateBuffered(){

        AtomicInteger onDispose = new AtomicInteger();
        AtomicInteger onCancel = new AtomicInteger();
        Flux<String> created = Flux.create(s -> {
            s.onDispose(onDispose::getAndIncrement)
                .onCancel(onCancel::getAndIncrement);
            s.next("test1");
            s.next("test2");
            s.next("test3");
            s.complete();
        });

        created.subscribe();

        Assert.assertEquals(onDispose.get(), 1);
        Assert.assertEquals(onCancel.get(), 0);

    }

    @Test(expected = Exception.class)
    public void flux_error(){
        Flux<String> created = Flux.create(s -> {
            s.error(new Exception("test"));
        });

        created.subscribe();
    }

    


}
