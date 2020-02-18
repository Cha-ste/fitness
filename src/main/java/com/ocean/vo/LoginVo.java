package com.ocean.vo;

import com.ocean.validator.IsMobile;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class LoginVo {
    @NotNull(message = "手机号码不能为空")
    @IsMobile
    private String mobile;

    @NotNull(message = "密码不能为空")
    @Length(message = "密码长度不能小于6且不能大于20", min=6, max = 20)
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
