package com.ocean.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ManagerChangePasswordVo {
    @NotEmpty(message = "管理员账号不能为空")
    private Integer managerName;
    @NotEmpty(message = "管理员密码不能为空")
    private String managerPassword;
    @NotEmpty(message = "id不能为空")
    private Integer id;
    @NotEmpty(message = "用户类型不能为空，member-会员，coach-教练")
    private String userType;
    @NotEmpty(message = "新密码不能为空")
    private String newPassword;
}
