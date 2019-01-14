package com.example.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenerateTests {

    /*
    레퍼런스
    https://github.com/reactor/reactor-core/blob/master/reactor-core/src/test/java/reactor/core/publisher/FluxGenerateTest.java#L110

     */


    @Test(expected = NullPointerException.class)
    public void stateSupplierNull() {
        Flux.generate(
                null,
                (s, synchronousSink) -> s,
                s -> { }
            );
    }


    @Test
    public void generateJust() {
        /*
        Flux.<Integer>generate(o -> {
            o.next(1);
            o.complete();
        }).subscribe();
        */
    }


    @Test
    public void generate_test(){

        /*
            Project Reactor 공식 레퍼런스
            https://projectreactor.io/docs/core/release/reference/#producing.generate

         */
        Flux<String> flux = Flux.generate(
                () -> 1, //1.초기 상태를 1 으로
                (state, sink) -> {

                    //2.We use the state to choose what to emit
                    sink.next("3 x " + state + " = " + 3*state);
                    if (state == 9) {
                        sink.complete(); //3.We also use it to choose when to stop.
                    }
                    //4.We return a new state that we use in the next invocation (unless the sequence terminated in this one).
                    return state + 1;
                });

        flux.subscribe(System.out::println,
                error -> System.out.println("에러"),
                () -> System.out.println("완료"));
    }

    @Test
    public void generate_test02() {

        List<Integer> list = new ArrayList<>();

        Flux.<Integer, Integer>generate(
                () -> 1,
                (state, sink) -> {
                    if (state < 11) {
                        sink.next(state);
                    }
                    else {
                        sink.complete();
                    }
                    return state + 1;
                })
                .subscribe(list::add);

        //expected: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        //TODO: assertArray....
        Assert.assertTrue(list.equals(expected));
    }


    @Test
    public void generate_test03() {

        List<Integer> list = new ArrayList<>();

        Flux.<Integer, Integer>generate(
                () -> 1,
                (state, sink) -> {
                    if (state < 11) {
                        sink.next(state);
                    }
                    else {
                        sink.complete();
                    }
                    return state + 1;
                })
                .subscribe(list::add);

        //expected: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        //TODO: assertArray....
        Assert.assertTrue(list.equals(expected));
    }

    @Test
    public void generate_iterableSource() {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Flux.<Integer, Iterator<Integer>>generate(list::iterator, (state, sink) -> {
            if (state.hasNext()) {
                sink.next(state.next());
            }
            else {
                sink.complete();
            }
            return state;
        }).subscribe();

        //TODO: 테스트
    }




}
