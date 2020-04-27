package com.sdk9.demo.spring;

import com.sdk9.demo.spring.entity.Product;
import com.sdk9.demo.spring.entity.SocketItem;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ChenHanLin 2020/4/23
 */

public class Stock {

    private static final Map<Product, SocketItem> stockItemMap = new ConcurrentHashMap<>();

    private SocketItem getItem(Product product){
        stockItemMap.putIfAbsent(product,new SocketItem());
        return stockItemMap.get(product);
    }

    public void store(Product product,long amount){
        getItem(product).store(amount);
    }

    public void remove(Product product,long amount){
        getItem(product).remove(amount);
    }
}
