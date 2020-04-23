package com.sdk9.demo.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单项
 *
 * @author ChenHanLin 2020/4/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    /**
     * 产品
     */
    private Product product;
    /**
     * 购买的库存
     */
    private long amount;

    public static Item of(Product product, long amount) {
        return new Item(product, amount);
    }
}
