package com.sdk9.demo.spring.hazelcast.config;

import lombok.Data;

/**
 * @author ChenHanLin 2020/7/8
 * 功能描述:K8s部署环境网络配置
 */
@Data
public class K8sSetting {
    /**
     * 是否启用该配置
     */
    private Boolean enable;
    /**
     * 服务网关
     */
    private String ServiceDns;
}