package com.ocean.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CoachLoginVO {
    @NotEmpty(message = "教练名字不能为空")
    private String coachName;
    @NotEmpty(message = "密码不能为空")
    private String password;
}
