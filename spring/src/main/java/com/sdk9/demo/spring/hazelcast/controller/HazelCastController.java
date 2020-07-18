package com.sdk9.demo.spring.hazelcast.controller;

import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * @author ChenHanLin 2020/7/8
 */
@RestController
@RequestMapping("hazelcast")
@Slf4j
public class HazelCastController {

    @Autowired
    private HazelcastInstance hazelcastInstance;

    @RequestMapping("map")
    public void map() {
        ConcurrentMap<String, String> map = hazelcastInstance.getMap("my-distributed-map");
        for (int i = 0; i < 1000; i++) {
            map.put("key" + i, "value");
        }
        LocalDateTime start = LocalDateTime.now();

        log.info("存数据共计用时:{}", Duration.between(start, LocalDateTime.now()));

        LocalDateTime read = LocalDateTime.now();
        ConcurrentMap<String, String> map2 = hazelcastInstance.getMap("my-distributed-map");
        System.out.println(map2.get("key750"));
        System.out.println(map2.get("key250"));
        System.out.println(map2.get("key999"));
        System.out.println(map2.get("key0"));
        log.info("读数据共计用时:{}", Duration.between(read, LocalDateTime.now()));
    }
}
