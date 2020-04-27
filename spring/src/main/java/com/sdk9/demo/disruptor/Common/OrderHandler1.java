package com.sdk9.demo.disruptor.Common;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import com.sdk9.demo.disruptor.entity.Order;

/**
 * @author ChenHanLin 2020/4/26
 */
public class OrderHandler1 implements EventHandler<Order>, WorkHandler<Order> {

    private String consumerId;

    private Integer i = 0;

    public OrderHandler1(String consumerId) {
        this.consumerId = consumerId;
    }

    @Override
    public void onEvent(Order order) {
        System.out.println("OrderHandler1" + this.consumerId + ",消费消息：" + order.getId());
        i++;
        System.out.println("i===========>" + i);
    }

    @Override
    public void onEvent(Order order, long l, boolean b) {
        System.out.println("OrderHandler1 " + this.consumerId + "，消费信息：" + order.getId());
        i++;
        System.out.println("i===========>" + i);
    }
}
