package com.sdk9.demo.disruptor.process;

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
 * 单生产者，多消费者，多个消费者 不重复 消费
 *
 * @author ChenHanLin 2020/4/26
 */
public class P1CnWorkPool {

    public static void main(String[] args) throws Exception {
        EventFactory<Order> factory = new OrderFactory();
        int ringBufferSize = 1024 * 1024;
        Disruptor<Order> disruptor = new Disruptor<>(factory, ringBufferSize, Executors.defaultThreadFactory(), ProducerType.SINGLE, new YieldingWaitStrategy());

        // 设置 多个消费者 ，对同一条消息只会有一个消费者消费
        disruptor.handleEventsWithWorkerPool(new OrderHandler1("1"), new OrderHandler1("2"));
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
