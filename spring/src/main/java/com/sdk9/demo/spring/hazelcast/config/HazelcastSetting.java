package com.sdk9.demo.spring.hazelcast.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ChenHanLin 2020/7/8
 */
@Component
@ConfigurationProperties(prefix = "hazelcast")
@Data
public class  HazelcastSetting {
    /**
     * 是否使用自定义配置
     */
    private Boolean selfSetting;
    /**
     * 主动发现网络配置
     */
    private TcpIpSetting tcpIp;
    /**
     *
     */
    private K8sSetting kubernetes;
}