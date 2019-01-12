package com.example.demo;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class EddySubscription implements Subscription {

    private Logger logger = LoggerFactory.getLogger(EddySubscription.class);

    private final ExecutorService executorService;
    private Subscriber<? super Integer> subscriber;
    private final AtomicInteger value;

    EddySubscription(Subscriber<? super Integer> subscriber,
                     ExecutorService executorService) {
        this.subscriber = subscriber;
        this.executorService = executorService;

        value = new AtomicInteger();
    }

    @Override
    public void request(long n) {
        if(n < 0){//TODO:error
        }
        else{
            for(int i = 0; i < n; i++){
                //new Runnable()
                executorService.execute(() -> {
                    int count = value.incrementAndGet();

                    if(count > 1000){
                        logger.info("Item is over ");
                        subscriber.onComplete();
                    }
                    else{
                        logger.info("push Item + " + count);
                        subscriber.onNext(count);
                    }
                });
            }
        }
    }

    @Override
    public void cancel() {
        subscriber.onComplete();
    }


}
