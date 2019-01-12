package com.example.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
    }


    @Test
    public void test01(){

        List<String> names = new ArrayList<>();

        Flux<String> flux = Flux.just("에디킴", "아이린").log();
        flux.subscribe(names::add);

        Assert.assertEquals(names, Arrays.asList("에디킴", "아이린"));

    }

    @Test
    public void test02(){

        List<String> names = new ArrayList<>();

        Flux<String> flux = Flux.just("에디킴", "아이린").log();
        flux.subscribe(names::add);

        Assert.assertNotEquals(names, Arrays.asList("에디킴"));
    }

    @Test
    public void test03(){

        List<String> names = new ArrayList<>();

        Flux<String> flux = Flux.fromArray(new String[]{"에디킴", "아이유", "아이린"});
        flux.subscribe(names::add);

        Assert.assertEquals(names, Arrays.asList("에디킴", "아이유", "아이린"));

    }


    @Test
    public void test04(){

        List<String> names = new ArrayList<>();

        Flux<String> flux = Flux.fromIterable(Arrays.asList("아이유", "아이린"));
        flux.subscribe(names::add);

        Assert.assertEquals(names, Arrays.asList("아이유", "아이린"));

    }

    @Test
    public void test05(){

        List<String> names = new ArrayList<>();

        Flux<String> flux = Flux.empty();
        flux.subscribe(names::add);

        Assert.assertEquals(names.size(), 0);

    }





}

