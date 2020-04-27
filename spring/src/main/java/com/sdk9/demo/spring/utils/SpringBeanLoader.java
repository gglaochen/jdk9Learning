package com.sdk9.demo.spring.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import java.util.Map;

/**
 * Spring容器工具类
 * @author ChenHanLin 2020/3/6
 */
public class SpringBeanLoader {

    private static ApplicationContext applicationContext;

    /**
     * 获取SpringApplicationContext
     *
     * @return ApplicationContext
     */

    private static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 设置SpringApplicationContext
     *
     * @param applicationContext
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringBeanLoader.applicationContext = applicationContext;
    }

    /**
     * 获取当前Spring容器中注册的bean
     */
    public static <T> T getApplicationBean(Class<T> beanClass) {
        return ContextLoader.getCurrentWebApplicationContext().getBean(beanClass);
    }

    /**
     * 获取Spring中注册的Bean
     *
     * @param beanClass
     * @param beanId
     * @return
     */
    public static <T> T getSpringBean(String beanId, Class<T> beanClass) {
        return getApplicationContext().getBean(beanId, beanClass);
    }

    /**
     * 获取Spring中注册的Bean
     *
     * @param beanClass
     * @return
     */
    public static <T> T getSpringBean(Class<T> beanClass) {
        return getApplicationContext().getBean(beanClass);
    }

    /**
     * 获取一个SpringBean的所有实例
     */
    public static <T> Map<String, T> getBeanMap(Class<T> beanClass) {
        return getApplicationContext().getBeansOfType(beanClass);
    }
}
