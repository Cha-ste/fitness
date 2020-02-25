/*
*
* User.java
* Copyright(C) 2017-2020 ocean
* @date 2020-02-19
*/
package com.ocean.entity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class User {
    private Integer sid;

    @NotEmpty(message = "username can not be empty")
    private String username;

    @NotEmpty(message = "password can not be empty")
    private String password;

    private Integer sex;

    private String phone;

    private Date birthday;

    private BigDecimal money;
}