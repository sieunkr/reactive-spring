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
public class FilterTests {


    @Test
    public void flux_filter(){

        List<String> colors = new ArrayList<>();

        Flux<String> flux = Flux.just("blue", "green", "orange", "purple").log();

        flux.filter(color -> !color.equals("blue"))
                .subscribe(colors::add);

        Assert.assertEquals(colors.size(), 3);
        Assert.assertTrue(colors.contains("green"));
        Assert.assertFalse(colors.contains("blue"));
    }


    @Test
    public void flux_take(){

        List<String> colors = new ArrayList<>();

        Flux<String> flux = Flux.just("blue", "green", "orange", "purple").log();

        flux.take(2)
                .subscribe(colors::add);

        Assert.assertEquals(colors.size(), 2);
        Assert.assertTrue(colors.contains("green"));
        Assert.assertFalse(colors.contains("orange"));

    }


    @Test
    public void flux_skip(){

        List<String> colors = new ArrayList<>();

        Flux<String> flux = Flux.just("blue", "green", "orange", "purple").log();


        flux.skip(3)
                .subscribe(colors::add);

        Assert.assertEquals(colors.size(), 1);

        Assert.assertTrue(colors.contains("purple"));
        Assert.assertFalse(colors.contains("blue"));

    }

    
    @Test
    public void flux_repeat(){

        List<String> names = new ArrayList<>();

        Flux<String> nameFlux = Flux.just("에디킴", "아이린").doOnEach(signal -> {
            //TODO: signal check
        });
        nameFlux.repeat(2).subscribe(names::add);

        Assert.assertEquals(names.size(), 6);


    }

}
