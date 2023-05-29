package com.t4mako.bookmanagesystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author T4mako
 * @date 2023/5/28 10:43
 */
@Data
public class Book {
    @TableId(type = IdType.AUTO,value = "id") //设置主键自增
    private Integer id;
    private String isbn;
    private String name;
    private String publisher;
    private BigDecimal price;
    private Integer stock;
}
