package com.sdk9.demo.spring.flow;

import com.sdk9.demo.spring.Stock;
import com.sdk9.demo.spring.entity.Order;
import com.sdk9.demo.spring.utils.SpringBeanLoader;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.SubmissionPublisher;

/**
 * 订阅者
 *
 * @author ChenHanLin 2020/4/23
 */
@Slf4j
public class StockProcessor extends SubmissionPublisher<Order>
        implements Flow.Processor<Order, Order> {

    private Stock stock;

    public StockProcessor() {
        this.stock = new Stock();
    }

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
        log.error("进入onError方法");
    }

    @Override
    public void onComplete() {
        log.info("onComplete");
    }
}
