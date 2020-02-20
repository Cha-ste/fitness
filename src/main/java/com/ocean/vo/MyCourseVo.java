package com.ocean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(value = "我的健身课程信息")
public class MyCourseVo {
    private Integer cid;
    private Integer tid;
    private String cname;
    private BigDecimal cost;
    private String coachName;
    private String location;
    private String description;
    private String count;

    @ApiModelProperty("上课时间")
    private Date ctime;

    @ApiModelProperty("设置上课时间标志")
    private String setTime;

    @ApiModelProperty("预约课程标志")
    private Integer appointment;

    @ApiModelProperty("确认预约标志")
    private String appointok;

    @ApiModelProperty("打卡标志")
    private String clockin;

    @ApiModelProperty("禁用标志")
    private String prohibit;

    @ApiModelProperty("剩余打卡次数")
    private Integer punch;

    @ApiModelProperty("我的课程评价")
    private String context;
}
