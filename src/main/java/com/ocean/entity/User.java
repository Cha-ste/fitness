/*
*
* User.java
* Copyright(C) 2017-2020 ocean
* @date 2020-02-19
*/
package com.ocean.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class User {
    private Integer sid;

    private String username;

    private String password;

    private Integer sex;

    private String phone;

    private Date birthday;

    private BigDecimal money;
}