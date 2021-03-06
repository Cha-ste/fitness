package com.ocean.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocean.vo.ApplyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ocean.entity.Apply;
import com.ocean.service.ApplyService;
import com.ocean.vo.ResultBean;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import com.github.pagehelper.PageInfo;

@RestController("ApplyController")
@RequestMapping("/apply")
@Api(tags = "报名相关接口")
public class ApplyController {

    public static Logger logger = LoggerFactory.getLogger(ApplyController.class);

    @Autowired
    private ApplyService service;

    @GetMapping(value = "/get")
    @ApiOperation("获取报名信息")
    public ResultBean<Apply> get(Integer cid, Integer sid) {
        try {
            Apply entity=service.getApply(cid, sid);
            return ResultBean.success(entity);
        } catch (Exception e) {
            logger.error("Fail:", e);
            return ResultBean.errorMsg("报名信息不存在");
        }
    }

    @GetMapping(value = "query/{pageNum}/{pageSize}")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public ResultBean<PageInfo<Apply>> query(
        int pageNum,
        int pageSize,
        @RequestParam(required = false) String param) {
        HashMap<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isEmpty(param)) {
            try {
                paramMap = new ObjectMapper().readValue(param, HashMap.class);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }else{
            paramMap.put("orderBy","id desc");
        }
        PageInfo<Apply> pageInfo = service.query(pageNum, pageSize, paramMap);
        return ResultBean.success(pageInfo);
    }

    @PostMapping(value = "/apply")
    @ApiOperation("会员报名")
    public ResultBean save(@Validated ApplyVO vo) {
        try {
            service.save(vo);

        } catch (Exception e) {
            logger.error("Fail:", e);
            return ResultBean.ERROR;
        }
        return ResultBean.success("报名成功");
    }

    @PostMapping(value = "/changeTable")
    @ApiOperation("修改会员报名信息")
    public ResultBean changeTable(@Validated Apply apply) {
        try {
            service.changeTable(apply);
        } catch (Exception e) {
            logger.error("Fail:", e);
            return ResultBean.errorMsg("报名信息不存在，修改失败");
        }
        return ResultBean.success("修改成功");
    }

    @PostMapping(value = "/del")
    @ApiOperation("删除报名记录")
    public ResultBean del(Integer cid, Integer sid) {
        try {
            service.del(cid, sid);
        } catch (Exception e) {
            logger.error("Fail:", e);
            return ResultBean.ERROR;
        }
        return ResultBean.success("删除成功");
    }

}
