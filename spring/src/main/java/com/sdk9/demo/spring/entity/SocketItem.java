package com.sdk9.demo.spring.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 库存
 *
 * @author ChenHanLin 2020/4/23
 */
@Data
@Builder
@NoArgsConstructor
public class SocketItem {
    /**
     * 库存
     */
    private final AtomicLong amount = new AtomicLong(0);

    public void store(long amount) {
        this.amount.accumulateAndGet(amount, Long::sum);
    }

    public void remove(long amount) {
        if (amount > this.amount.get()) {
            throw new RuntimeException("库存不足");
        }
        this.amount.accumulateAndGet(amount, (pre, mount) -> pre - mount);
    }
}
