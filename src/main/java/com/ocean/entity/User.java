/*
*
* User.java
* Copyright(C) 2017-2020 ocean
* @date 2019-09-28
*/
package com.ocean.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户对象", description = "定义对象的基本信息")
public class User {
    private String id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户名")
    private String password;
    @ApiModelProperty("头像")
    private String faceImage;
    @ApiModelProperty("昵称")
    private String nickname;
    @ApiModelProperty("粉丝数")
    private Integer fansCounts;
    @ApiModelProperty("用户名")
    private Integer followCounts;
    @ApiModelProperty("点赞数")
    private Integer receiveLikeCounts;
    @ApiModelProperty("手机")
    private String mobile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getFaceImage() {
        return faceImage;
    }

    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage == null ? null : faceImage.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Integer getFansCounts() {
        return fansCounts;
    }

    public void setFansCounts(Integer fansCounts) {
        this.fansCounts = fansCounts;
    }

    public Integer getFollowCounts() {
        return followCounts;
    }

    public void setFollowCounts(Integer followCounts) {
        this.followCounts = followCounts;
    }

    public Integer getReceiveLikeCounts() {
        return receiveLikeCounts;
    }

    public void setReceiveLikeCounts(Integer receiveLikeCounts) {
        this.receiveLikeCounts = receiveLikeCounts;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }
}