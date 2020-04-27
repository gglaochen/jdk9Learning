package com.jdk9.demo.webflux.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ChenHanLin 2020/4/27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    /**
     * id
     */
    private String id;
    /**
     * 书名
     */
    private String bookName;
}
