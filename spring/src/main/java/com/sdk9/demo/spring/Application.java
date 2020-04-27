package com.sdk9.demo.spring;

import com.sdk9.demo.spring.utils.SpringBeanLoader;
import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext run = SpringApplication.run(Application.class, args);
        SpringBeanLoader.setApplicationContext(run);
    }

}
