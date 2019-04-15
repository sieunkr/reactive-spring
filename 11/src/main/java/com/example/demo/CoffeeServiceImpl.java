package com.example.demo;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class CoffeeServiceImpl implements CoffeeService {

    @Override
    public Flux<Coffee> findAll() {
        return Flux.just(
                new Coffee("1", "mocha"),
                new Coffee("2", "latte"),
                new Coffee("3", "americano")
        );
    }
}
