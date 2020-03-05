/*
*
* Apply.java
* Copyright(C) 2017-2020 ocean
* @date 2020-03-02
*/
package com.ocean.entity;

import java.util.Date;

public class Apply {
    private Integer cid;

    private Integer sid;

    private Integer tid;

    private Date ctime;

    private String settime;

    private Integer appointment;

    private String appointok;

    private String clockin;

    private String prohibit;

    private Integer punch;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getSettime() {
        return settime;
    }

    public void setSettime(String settime) {
        this.settime = settime == null ? null : settime.trim();
    }

    public Integer getAppointment() {
        return appointment;
    }

    public void setAppointment(Integer appointment) {
        this.appointment = appointment;
    }

    public String getAppointok() {
        return appointok;
    }

    public void setAppointok(String appointok) {
        this.appointok = appointok == null ? null : appointok.trim();
    }

    public String getClockin() {
        return clockin;
    }

    public void setClockin(String clockin) {
        this.clockin = clockin == null ? null : clockin.trim();
    }

    public String getProhibit() {
        return prohibit;
    }

    public void setProhibit(String prohibit) {
        this.prohibit = prohibit == null ? null : prohibit.trim();
    }

    public Integer getPunch() {
        return punch;
    }

    public void setPunch(Integer punch) {
        this.punch = punch;
    }
}
