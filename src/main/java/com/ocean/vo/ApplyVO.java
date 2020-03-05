package com.ocean.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ApplyVO {
    @NotNull(message = "cid 不能为空")
    private Integer cid;
    @NotNull(message = "tid 不能为空")
    private Integer tid;
    @NotNull(message = "sid 不能为空")
    private Integer sid;
    @NotNull(message = "punch 不能为空")
    private Integer punch;
}
