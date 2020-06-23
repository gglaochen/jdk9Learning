package com.sdk9.demo.agrona;

import com.carrotsearch.sizeof.RamUsageEstimator;
import lombok.extern.slf4j.Slf4j;
import org.agrona.collections.LongArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ChenHanLin 2020/6/8
 */
@Slf4j
public class main {
/*    public static void main(String[] args) {
        LocalDateTime before2 = LocalDateTime.now();

        System.out.println("使用List<Integer>使用了" + Duration.between(LocalDateTime.now(), before2));
        LocalDateTime before = LocalDateTime.now();
        LongArrayList longs = new LongArrayList();
        for (long i = 0; i < 100000; i++) {
            longs.add(i);
        }
        System.out.println(longs.get(500));
        System.out.println(longs.get(250));
        System.out.println(longs.get(750));
        System.out.println("使用LongArrayList使用了" + Duration.between(LocalDateTime.now(), before));

    }*/

    public static void main(String[] args) {
        List<Integer> a = new ArrayList<>();
        LocalDateTime localDateTime = LocalDateTime.now();
        for (int i = 0; i < 100000; i++) {
            a.add(i);
        }
        log.info("JDK List添加十万条数据用时：{},占用内存空间:{}", Duration.between(LocalDateTime.now(), localDateTime), RamUsageEstimator.sizeOf(a));

        IntArrayList b = new IntArrayList();
        for (int i = 0; i < 100000; i++) {
            b.add(i);
        }
        LocalDateTime localDateTime2 = LocalDateTime.now();
        log.info("eclipce List添加十万条数据用时：{},占用内存空间:{}", Duration.between(LocalDateTime.now(), localDateTime2), RamUsageEstimator.sizeOf(b));

        org.agrona.collections.IntArrayList c = new org.agrona.collections.IntArrayList();
        for (int i = 0; i < 100000; i++) {
            c.add(i);
        }
        LocalDateTime localDateTime3 = LocalDateTime.now();
        List<String> d = c.stream().map(String::valueOf).collect(Collectors.toList());
        log.info("agrona List添加十万条数据用时：{},占用内存空间:{}", Duration.between(LocalDateTime.now(), localDateTime3), RamUsageEstimator.sizeOf(c));
    }
}
