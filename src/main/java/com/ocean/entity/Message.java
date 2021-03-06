/*
*
* Message.java
* Copyright(C) 2017-2020 ocean
* @date 2020-02-19
*/
package com.ocean.entity;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

public class Message {
    private Integer nid;

    @NotEmpty(message = "title 不能为空")
    private String title;

    @NotEmpty(message = "context 不能为空")
    private String context;

    private Date ptime;

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    public Date getPtime() {
        return ptime;
    }

    public void setPtime(Date ptime) {
        this.ptime = ptime;
    }
}