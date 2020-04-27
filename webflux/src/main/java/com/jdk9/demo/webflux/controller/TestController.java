package com.jdk9.demo.webflux.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author ChenHanLin 2020/4/27
 */
@Slf4j
@RestController
public class TestController {
    private String createStr() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = "str.." + UUID.randomUUID();
        log.info(str);
        return str;
    }

    @GetMapping("test")
    public Mono<Object> test() {
        log.info("调用test方法");
        return Mono.create(obj -> obj.success(createStr()))
                .doOnSubscribe(sub -> {
                    log.info("订阅test成功");
                })
                .doOnNext(obj -> {
                    log.info("next");
                });
    }

    @GetMapping(value = "/eventStrem", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<String> flux() {
        Flux<String> result = Flux
                .fromStream(IntStream.range(1, 5).mapToObj(i -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                    }
                    return "flux data--" + i;
                }));
        return result;
    }
}
