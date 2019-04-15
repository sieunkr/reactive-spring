package com.example.demo;

import reactor.core.publisher.Flux;

public interface CoffeeService {
    Flux<Coffee> findAll();
}
