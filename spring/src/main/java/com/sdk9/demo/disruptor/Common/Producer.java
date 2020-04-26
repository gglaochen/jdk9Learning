package com.sdk9.demo.disruptor.Common;

import com.lmax.disruptor.RingBuffer;
import com.sdk9.demo.disruptor.entity.Order;

/**
 * @author ChenHanLin 2020/4/26
 */
public class Producer {
    private final RingBuffer<Order> ringBuffer;

    public Producer(RingBuffer<Order> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(String data) {
        long sequence = ringBuffer.next();
        try {
            Order order = ringBuffer.get(sequence);
            order.setId(data);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
