package com.example.demo;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySubscriber implements Subscriber<String> {

    Logger logger = LoggerFactory.getLogger(MySubscriber.class);

    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription s) {
        subscription = s;
        subscription.request(1);
    }

    @Override
    public void onNext(String name) {
        logger.info("시퀀스 수신 : " + name);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable t) {
        logger.info("에러 : " + t.getMessage());
    }

    @Override
    public void onComplete() {
        logger.info("모든 시퀀스 수신 완료");
    }
}
