package com.sdk9.demo.spring.hazelcast.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.config.JetConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.Objects;

/**
 * @author ChenHanLin 2020/7/8
 */
@Configuration
@DependsOn("hazelcastSetting")
public class HazelcastConfig {

    private String app_name = "match";

    // @Value("${spring.profiles.active}")
    private String app_active = "local";

    @Autowired
    HazelcastSetting hazelcastSetting;

    @Bean
    public Config config() {
        String instanceKey = app_name + app_active + "-instance";
        Config config = new Config();
        config.setInstanceName(instanceKey);
        networkSetting(config);
        return config;
    }

    @Bean
    public JetInstance jetInstance(Config config) {
        JetConfig jConfig = new JetConfig();
        jConfig.setHazelcastConfig(config);
        return Jet.newJetInstance(jConfig);
    }

    @Bean
    public HazelcastInstance hzInstance(JetInstance jetInstance) {
        return jetInstance.getHazelcastInstance();
    }

    /**
     * 网络配置
     *
     * @param config
     */
    private void networkSetting(Config config) {
        //未开启自定义网络配置
        if (Objects.isNull(hazelcastSetting.getSelfSetting()) || !hazelcastSetting.getSelfSetting()) {
            return;
        }
        NetworkConfig network = config.getNetworkConfig();
        JoinConfig join = network.getJoin();
        //主动发现配置
        if (Objects.nonNull(hazelcastSetting.getTcpIp()) && hazelcastSetting.getTcpIp().getEnable()) {
            TcpIpSetting tcpIpSetting = hazelcastSetting.getTcpIp();
            join.getMulticastConfig().setEnabled(Boolean.FALSE);
            tcpIpSetting.getMembers().forEach(item ->
                    join.getTcpIpConfig().addMember(item).setRequiredMember(item).setEnabled(Boolean.TRUE)
            );
            network.getInterfaces().setEnabled(Boolean.TRUE).addInterface(hazelcastSetting.getTcpIp().getInterfaceEx());
            config.setNetworkConfig(network);
            return;
        }
        //K8s网络发现
        if (Objects.nonNull(hazelcastSetting.getKubernetes()) && hazelcastSetting.getKubernetes().getEnable()) {

            join.getMulticastConfig().setEnabled(Boolean.FALSE);
            join.getKubernetesConfig().setEnabled(Boolean.TRUE)
                    .setProperty("service-dns", hazelcastSetting.getKubernetes().getServiceDns());
            config.setNetworkConfig(network);
            return;
        }
    }
}