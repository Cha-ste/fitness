/*
*
* Coach.java
* Copyright(C) 2017-2020 ocean
* @date 2020-02-19
*/
package com.ocean.entity;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

public class Coach {
    private Integer tid;

    @NotEmpty(message = "coachName 不能为空")
    private String coachName;

    @NotEmpty(message = "password 不能为空")
    private String password;

    private Integer sex;

    private String phone;

    private Date birthday;

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName == null ? null : coachName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}