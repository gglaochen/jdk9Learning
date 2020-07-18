package com.sdk9.demo.spring.hazelcast.config;

import lombok.Data;

import java.util.List;

/**
 * @author ChenHanLin 2020/7/8
 * 主动发现配置
 */
@Data
public class TcpIpSetting {
    /**
     * 是否启用该配置
     */
    private Boolean enable;
    /**
     * 网关表达式
     */
    private String interfaceEx;
    /**
     * 实例集合
     */
    private List<String> members;
}