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

        Assert.assertEquals(2,names.size());
        Assert.assertEquals("eddy",names.get(0));
        Assert.assertEquals("irene",names.get(1));

        Assert.assertNotEquals("EDDY",names.get(0));
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

        Assert.assertEquals("Eddy Kim",names.get(0));
        Assert.assertEquals( "Alice Lee",names.get(1));
    }

    @Test
    public void flux_flatmap_integer(){

        List<Integer> integerList = new ArrayList<>();

        Flux<Integer> integerFlux = Flux.just(1, 2, 3).log();

        integerFlux.flatMap(i -> Flux.range(0, i))
                .subscribe(integerList::add);

        // expected : 0, 0, 1, 0, 1, 2
        List<Integer> expected = Arrays.asList(0,0,1,0,1,2);
        Assert.assertEquals(6,integerList.size());

        //TODO:assertArrays..
        Assert.assertEquals(expected, integerList);
    }

    @Test
    public void flux_flatmap_string(){

        List<String> keywords = new ArrayList<>();

        Flux<String> flux = Flux.just("I like you","I hate you");
        flux.flatMap(s -> Flux.fromArray(s.split(" ")))
                .subscribe(keywords::add);

        //expected : I, like, you, I, hate, you
        Assert.assertEquals(6,keywords.size());
        Assert.assertEquals("I", keywords.get(0));
        Assert.assertEquals("like", keywords.get(1));
        Assert.assertEquals("you", keywords.get(2));
        Assert.assertEquals("I like you",
                keywords.get(0) + " " + keywords.get(1)
                        + " " + keywords.get(2));
    }

    @Test
    public void flux_toIterable(){

        Iterator<String> iterator = Flux.just("Eddy", "Irene").toIterable().iterator();

        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("Eddy", iterator.next());

        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("Irene", iterator.next());

        //Assert.assertFalse(iterator.hasNext());
    
    }

}
