package com.sdk9.demo.spring.flow;

import com.sdk9.demo.spring.entity.Order;
import com.sdk9.demo.spring.handler.Stock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow;
import java.util.concurrent.ForkJoinPool;

/**
 * 订阅者
 *
 * @author ChenHanLin 2020/4/23
 */
@Slf4j
public class StockSubscriber implements Flow.Subscriber<Order> {

    @Autowired
    private Stock stock;

    private Flow.Subscription subscription = null;

    private ExecutorService executorService = ForkJoinPool.commonPool();

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        log.info("******调用 onSubscribe******");
        subscription.request(3);
        this.subscription = subscription;
    }

    @Override
    public void onNext(Order item) {
        executorService.submit(() -> {
            log.info("Thread {}", Thread.currentThread().getName());
            item.getItem().forEach(x -> {
                stock.remove(x.getProduct(), x.getAmount());
            });
            subscription.request(1);
        });
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
