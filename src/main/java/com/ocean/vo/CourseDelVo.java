package com.ocean.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CourseDelVo {
    @NotEmpty(message = "cid不能为空")
    private Integer cid;
    @NotEmpty(message = "tid不能为空")
    private Integer tid;
}
