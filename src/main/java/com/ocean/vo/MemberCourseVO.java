package com.ocean.vo;

import lombok.Data;

import java.util.Date;

@Data
public class MemberCourseVO {
    private Integer sid;
    private String username;
    private Integer sex;
    private String phone;
    private Date birthday;
    private Integer cid;
    private String title;
    private Date ctime;
    private Boolean setTime;
    private Boolean appointment;
    private Boolean appointok;
    private Boolean clockin;
}
