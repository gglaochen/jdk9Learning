package com.sdk9.demo.disruptor.consumer;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.sdk9.demo.disruptor.Common.OrderFactory;
import com.sdk9.demo.disruptor.Common.OrderHandler1;
import com.sdk9.demo.disruptor.Common.Producer;
import com.sdk9.demo.disruptor.entity.Order;

import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * 单生产者，单消费者
 *
 * @author ChenHanLin 2020/4/26
 */
public class P1C1 {

    public static void main(String[] args) throws Exception {
        EventFactory<Order> factory = new OrderFactory();
        int ringBufferSize = 1024 * 1024;
        //多生产者只需将 ProducerType.SINGLE 改成 ProducerType.MULTI
        Disruptor<Order> disruptor = new Disruptor<>(factory, ringBufferSize, Executors.defaultThreadFactory(), ProducerType.SINGLE, new YieldingWaitStrategy());

        // 设置一个消费者
        disruptor.handleEventsWith(new OrderHandler1("1"));
        disruptor.start();
        RingBuffer<Order> ringBuffer = disruptor.getRingBuffer();
        Producer producer = new Producer(ringBuffer);
        // 单生产者，生产3条数据
        IntStream.range(0, 3).forEach(i -> producer.onData(i + ""));

        //为了保证消费者线程已经启动，留足够的时间
        Thread.sleep(1000);
        disruptor.shutdown();
    }
}
