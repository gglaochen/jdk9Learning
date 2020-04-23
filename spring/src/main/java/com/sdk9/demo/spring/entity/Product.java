package com.sdk9.demo.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 产品
 *
 * @author ChenHanLin 2020/4/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    /**
     * 产品id
     */
    private String productId;

    /**
     * 产品名称
     */
    private String productName;

}
