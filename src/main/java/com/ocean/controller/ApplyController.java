package com.ocean.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.ocean.entity.Apply;
import com.ocean.service.ApplyService;
import com.ocean.vo.ResultBean;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import com.github.pagehelper.PageInfo;

@RestController("ApplyController")
@RequestMapping("/Apply")
@Api(tags = "报名相关接口")
public class ApplyController {

    public static Logger logger = LoggerFactory.getLogger(ApplyController.class);

    @Autowired
    private ApplyService service;

    @GetMapping(value = "/get")
    @ApiOperation("获取报名信息")
    public ResultBean<Apply> get(Integer id) {
        try {
            Apply entity=service.getApply(id);
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

    @PostMapping(value = "/save")
    public ResultBean save(Apply model) {
        try {
            Apply record = new Apply();
            BeanUtils.copyProperties(model, record);

            if (record.getCid() == null) {
                //TODO 设置主键
                service.save(record);
            } else {
                service.update(record);
            }

        } catch (Exception e) {
            logger.error("Fail:", e);
            return ResultBean.ERROR;
        }
        return ResultBean.success("保存成功");
    }

    @DeleteMapping(value = "/del", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBean del(String id) {
        try {
            service.del(id);
        } catch (Exception e) {
            logger.error("Fail:", e);
            return ResultBean.ERROR;
        }
        return ResultBean.success("删除成功");
    }

}