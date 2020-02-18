package com.ocean.entity;

import lombok.Data;

@Data
public class Goods {
    private Long id;
    private String goodsName;
    private String title;
    private String image;
    private Double price;
    private String detail;
    private Integer stock;
}
