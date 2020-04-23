package com.sdk9.demo.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 订单
 *
 * @author ChenHanLin 2020/4/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    /**
     * 订单项
     */
    private List<Item> item;

    public static Order of(List<Item> items) {
        return new Order(items);
    }
}
