package com.sdk9.demo.disruptor.Common;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import com.sdk9.demo.disruptor.entity.Order;
import lombok.AllArgsConstructor;

/**
 * @author ChenHanLin 2020/4/26
 */
@AllArgsConstructor
public class OrderHandler1 implements EventHandler<Order>, WorkHandler<Order> {

    private String consumerId;

    @Override
    public void onEvent(Order order) {
        System.out.println("OrderHandler1" + this.consumerId + ",消费消息：" + order.getId());
    }

    @Override
    public void onEvent(Order order, long l, boolean b) {
        System.out.println("OrderHandler1 " + this.consumerId + "，消费信息：" + order.getId());
    }
}
