package com.sdk9.demo.disruptor.entity;

import lombok.Builder;
import lombok.Data;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

/**
 * 行情数据
 *
 * @author ChenHanLin 2020/6/23
 */
@Data
@Builder
@Measurement(name = "quotes")
public class Quotes {
    /**
     * InfluxDB中时间戳均是以UTC时保存,在保存以及提取过程中需要注意时区转换
     */
    @Column(name = "time")
    private String time;

    /**
     * 证券名称
     */
    @Column(name = "secName", tag = true)
    private String secName;

    /**
     * 开盘价
     */
    @Column(name = "openPrice")
    private String openPrice;

    /**
     * 最新价
     */
    @Column(name = "newPrice")
    private String newPrice;
}
