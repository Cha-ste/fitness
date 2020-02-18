package com.ocean.entity;

import lombok.Data;

import java.util.Date;

@Data
public class MiaoShaGoods {
    private Long id;
    private Long goodsId;
    private Double price;
    private Integer stock;
    private Date startDate;
    private Date endDate;

}
