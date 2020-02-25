package com.ocean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "健身课程信息")
public class CourseVO {
    private Integer cid;
    private String cname;
    private BigDecimal cost;
    private String coachName;
    private String location;
    private String description;
    private String count;
    @ApiModelProperty(value = "会员是否已经购买本课程")
    private Integer havePaid;
}
