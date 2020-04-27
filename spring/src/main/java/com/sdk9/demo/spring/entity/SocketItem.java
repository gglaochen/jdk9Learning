package com.sdk9.demo.spring.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 库存
 *
 * @author ChenHanLin 2020/4/23
 */
@Data
@Builder
@NoArgsConstructor
@Slf4j
public class SocketItem {
    /**
     * 库存
     */
    private final AtomicLong amount = new AtomicLong(0);

    public void store(long amount) {
        this.amount.accumulateAndGet(amount, Long::sum);
        log.info("存储{} ,当前剩余{}", amount, this.amount.get());
    }

    public void remove(long amount) {
        if (amount > this.amount.get()) {
            throw new RuntimeException("库存不足");
        }
        this.amount.accumulateAndGet(amount, (pre, mount) -> pre - mount);
        log.info("移除{} ,当前剩余{}", amount, this.amount.get());
    }
}
