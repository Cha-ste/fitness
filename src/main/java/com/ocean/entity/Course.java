/*
*
* Course.java
* Copyright(C) 2017-2020 ocean
* @date 2020-02-19
*/
package com.ocean.entity;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

public class Course {
    private Integer cid;

    @DecimalMin(value = "1")
    private Integer tid;

    @NotEmpty(message = "课程名称不能为空")
    private String cname;

    @DecimalMin(value = "0")
    private BigDecimal cost;

    @NotEmpty(message = "上课地址不能为空")
    private String location;

    @NotEmpty(message = "课程介绍不能为空")
    private String description;

    private Integer count;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname == null ? null : cname.trim();
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getCount() {
        return count == null ? 0 : count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}