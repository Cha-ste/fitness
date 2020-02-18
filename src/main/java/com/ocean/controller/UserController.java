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
import com.ocean.entity.User;
import com.ocean.service.UserService;
import com.ocean.vo.ResultBean;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import com.github.pagehelper.PageInfo;

@RestController("UserController")
@RequestMapping("/User")
@Api(tags = "用户相关接口")
public class UserController {

    public static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService service;

    @ApiOperation(value = "获取用户")
    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBean<User> get(String id) {
        try {
            User entity=service.getUser(id);
            return ResultBean.success(entity);
        } catch (Exception e) {
            logger.error("Fail:", e);
            return ResultBean.ERROR;
        }
    }

    @ApiOperation(value = "搜索用户")
    @GetMapping(value = "query/{pageNum}/{pageSize}")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public ResultBean<PageInfo<User>> query(
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
        PageInfo<User> pageInfo = service.query(pageNum, pageSize, paramMap);
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "保存用户")
    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBean save(@RequestBody User model) {
        try {
            User record = new User();
            BeanUtils.copyProperties(model, record);

            if (record.getId() == null) {
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

    @ApiOperation(value = "删除用户")
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