package com.ocean.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MemberChangePasswordVo {
    @NotEmpty(message = "sid不能为空")
    private Integer sid;
    @NotEmpty(message = "旧密码不能为空")
    private String oldPassword;
    @NotEmpty(message = "新密码不能为空")
    private String newPassword;
}
