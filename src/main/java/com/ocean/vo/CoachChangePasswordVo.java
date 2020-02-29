package com.ocean.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CoachChangePasswordVo {
    @NotEmpty(message = "tid不能为空")
    private Integer tid;
    @NotEmpty(message = "旧密码不能为空")
    private String oldPassword;
    @NotEmpty(message = "新密码不能为空")
    private String newPassword;
}
