package com.sdk9.demo.disruptor.Common;

import com.lmax.disruptor.EventFactory;
import com.sdk9.demo.disruptor.entity.Order;

/**
 * @author ChenHanLin 2020/4/26
 */
public class OrderFactory implements EventFactory<Order> {
    @Override
    public Order newInstance() {
        return new Order();
    }
}
