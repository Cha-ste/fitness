package com.ocean.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ApplyVO {
    @NotNull
    private Integer cid;
    @NotNull
    private Integer sid;
    @NotNull
    private Integer punch;
}
