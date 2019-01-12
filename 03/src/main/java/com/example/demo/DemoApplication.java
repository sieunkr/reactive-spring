package com.example.demo;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) {

        Publisher<Integer> eddyPublisher = new EddyPublisher();
        Subscriber<Integer> eddySubscriber = new EddySubscriber();
        eddyPublisher.subscribe(eddySubscriber);

    }
}

