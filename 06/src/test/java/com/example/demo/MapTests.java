package com.example.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapTests {

    @Test
    public void flux_map(){

        List<String> names = new ArrayList<>();

        Flux<String> flux = Flux.just("EDDY", "IRENE").log();
        flux.map(String::toLowerCase)
                .subscribe(names::add);

        Assert.assertNotEquals(names.get(0), "EDDY");
        Assert.assertEquals(names.get(0), "eddy");
        Assert.assertEquals(names.get(1), "irene");
    }


    @Test
    public void flux_zip_map(){

        List<String> names = new ArrayList<>();

        Flux<String> firstNameFlux = Flux.just("Eddy", "Alice");
        Flux<String> lastNameFlux = Flux.just("Kim", "Lee");

        Flux<String> fullNameFlux = Flux.zip(firstNameFlux, lastNameFlux)
                .map(t -> t.getT1() + " " + t.getT2())
                .log();

        fullNameFlux.subscribe(names::add);

        Assert.assertEquals(names.get(0), "Eddy Kim");
        Assert.assertEquals(names.get(1), "Alice Lee");
    }

    @Test
    public void flux_flatmap_integer(){

        List<Integer> integerList = new ArrayList<>();

        Flux<Integer> integerFlux = Flux.just(1, 2, 3).log();

        integerFlux.flatMap(i -> Flux.range(0, i))
                .subscribe(integerList::add);

        // expected : 0, 0, 1, 0, 1, 2
        List<Integer> expected = Arrays.asList(0,0,1,0,1,2);
        Assert.assertEquals(integerList.size(),6);

        //TODO:assertArrays..
        Assert.assertTrue(integerList.equals(expected));
    }

    @Test
    public void flux_flatmap_string(){

        List<String> keywords = new ArrayList<>();

        Flux<String> flux = Flux.just("I like you","I hate you");
        flux.flatMap(s -> Flux.fromArray(s.split(" ")))
                .subscribe(keywords::add);

        //expected : I, like, you, I, hate, you
        Assert.assertEquals(keywords.size(), 6);
        Assert.assertEquals(keywords.get(0) + " " + keywords.get(1) + " " + keywords.get(2), "I like you");
    }

    @Test
    public void flux_toIterable(){

        Iterator<String> iterator = Flux.just("Eddy", "Irene").toIterable().iterator();

        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(iterator.next(), "Eddy");

        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(iterator.next(), "Irene");

        Assert.assertFalse(iterator.hasNext());
    
    }

}
