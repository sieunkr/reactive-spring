package com.example.demo;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EddyPublisher implements Publisher<Integer> {

    private Logger logger = LoggerFactory.getLogger(EddyPublisher.class);
    private final ExecutorService executor = Executors.newFixedThreadPool(3);

    @Override
    public void subscribe(Subscriber<? super Integer> subscriber) {

        logger.info("publisher - subscribe");

        subscriber.onSubscribe(new EddySubscription(subscriber, executor));
    }
}
