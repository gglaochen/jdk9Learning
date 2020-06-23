package com.sdk9.demo.spring.controller;

import com.sdk9.demo.disruptor.entity.Quotes;
import com.sdk9.demo.spring.utils.InfluxDbUtils;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

/**
 * influxdb控制层
 *
 * @author ChenHanLin 2020/6/23
 */
@RestController
@RequestMapping("influx")
public class InfluxdbController {

    @Autowired
    private InfluxDbUtils influxDbUtils;

    @PostMapping("create")
    public void create(@RequestBody Quotes quotes) {
        Point point = Point.measurementByPOJO(quotes.getClass())
                .addFieldsFromPOJO(quotes)
                .time(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli(), TimeUnit.MILLISECONDS).build();
        influxDbUtils.getInfluxDB().write(influxDbUtils.getDatabase(), InfluxDbUtils.policyNamePix, point);
    }

    @GetMapping("query")
    public String query(String secName) {
        QueryResult queryResult = influxDbUtils.getInfluxDB().query(new Query(String.format("SELECT * FROM quotes where secName = '%s'", secName), influxDbUtils.getDatabase()));
        return queryResult.getResults().get(0).getSeries().get(0).getValues().toString();
    }
}
