package com.sdk9.demo.spring.flow;

import com.sdk9.demo.spring.ApplicationTests;
import com.sdk9.demo.spring.entity.Item;
import com.sdk9.demo.spring.entity.Order;
import com.sdk9.demo.spring.entity.Product;
import com.sdk9.demo.spring.handler.Stock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.IntStream;

/**
 * @author ChenHanLin 2020/4/23
 */
@Slf4j
public class FlowTests extends ApplicationTests {


    private static void demoSubscribe(SubmissionPublisher<Order> publisher, String subscriberName) {
        StockSubscriber subscriber = new StockSubscriber();
        publisher.subscribe(subscriber);
    }

    @Test
    public void testStockRemoval() {
        SubmissionPublisher<Order> p = new SubmissionPublisher<>();
        //绑定订阅关系
        demoSubscribe(p, "One");
        demoSubscribe(p, "Two");
        demoSubscribe(p, "Three");
        //发布数据
        publish(p);
    }

    private void publish(SubmissionPublisher<Order> p) {
        Stock stock = new Stock();
        Product product = new Product();
        //给产品存40库存
        stock.store(product, 40);
        //一个订单项买10库存
        Item item = Item.of(product, 10);
        List<Item> items = new LinkedList<>();
        items.add(item);
        //一个订单 只有一个订单项
        Order order = Order.of(items);

        // 发布者提交 10次订单
        IntStream.of(10).forEach(x -> p.submit(order));

        log.info("所有订单已经提交完毕");
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        p.close();
    }

}
