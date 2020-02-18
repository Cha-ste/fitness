package com.ocean.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Orders {
    private Long id;
    private Long userId;
    private Long goodsId;
    private Long deliveryAddrId;
    private String goodsName;
    private Integer goodsCount;
    private Double goodsPrice;
    private String orderChanel;
    private String status;
    private Date createDate;
    private Date payDate;
}
