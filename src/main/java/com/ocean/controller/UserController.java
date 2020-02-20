package com.ocean.controller;

import com.github.pagehelper.PageInfo;
import com.ocean.entity.User;
import com.ocean.service.UserService;
import com.ocean.utils.MD5Util;
import com.ocean.vo.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController("UserController")
@RequestMapping("/user")
@Api(value = "会员", tags = "会员相关接口")
public class UserController {

    public static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService service;

    @PostMapping(value = "/register")
    @ApiOperation(value = "会员注册")
    public ResultBean<String> register(@RequestBody User user) {
        if (StringUtils.isEmpty(user.getUsername())) {
            ResultBean.errorMsg("用户名不能为空");
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            ResultBean.errorMsg("密码不能为空");
        }
        if (service.usernameExist(user.getUsername())) {
            ResultBean.errorMsg("用户名已经存在");
        }

        service.save(user);
        return ResultBean.success("注册成功");
    }

    @PostMapping(value = "/login")
    @ApiOperation(value = "会员登录")
    public ResultBean<User> login(@RequestBody User user) {
        if (StringUtils.isEmpty(user.getUsername())) {
            ResultBean.errorMsg("用户名不能为空");
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            ResultBean.errorMsg("密码不能为空");
        }

        User member = service.getUserForLogin(user);
        if (member == null) {
            return ResultBean.errorMsg("账号密码不正确");
        }
        return ResultBean.success(member);
    }


    @GetMapping(value = "/getUserInfo")
    @ApiOperation("获取会员信息")
    public ResultBean<User> getUserInfo(Integer sid) {
        if (sid == null || sid.equals(0)) {
            return ResultBean.errorMsg("参数错误，sid 没有传递");
        }
        try {
            User entity = service.getUser(sid);
            entity.setPassword(null); // 隐藏密码
            return ResultBean.success(entity);
        } catch (Exception e) {
            logger.error("Fail:", e);
            return ResultBean.ERROR;
        }
    }

    @GetMapping(value = "query")
    @ResponseBody
    @ApiOperation("获取会员列表（带分页，可根据会员名搜索）")
    public ResultBean<PageInfo<User>> query(
            @RequestParam(required = false) Integer pageNum,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String username) {
        if (pageNum == null || pageNum.equals(0)) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize.equals(0)) {
            pageSize = 20;
        }
        if (StringUtils.isEmpty(username)) {
            username = null;
        }

        PageInfo<User> pageInfo = service.query(pageNum, pageSize, username);
        return ResultBean.success(pageInfo);
    }

    @PostMapping(value = "/save")
    @ApiOperation("修改会员信息")
    public ResultBean save(@RequestBody User model) {
        try {
            User record = new User();
            BeanUtils.copyProperties(model, record);

            if (record.getSid() == null) {
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

    @PostMapping(value = "/del")
    @ApiOperation("删除会员")
    public ResultBean del(Integer sid) {
        if (com.ocean.utils.StringUtils.isNullOrZero(sid)) {
            return ResultBean.errorMsg("参数错误：sid没有传递");
        }
        try {
            service.del(sid);
        } catch (Exception e) {
            logger.error("Fail:", e);
            return ResultBean.errorMsg("会员不存在，删除失败");
        }
        return ResultBean.success("删除成功");
    }

    @PostMapping(value = "/changePassword")
    @ApiOperation("修改会员密码")
    public ResultBean changePassword(Integer sid, String oldPassword, String newPassword) {
        if (com.ocean.utils.StringUtils.isNullOrZero(sid)) {
            return ResultBean.errorMsg("参数错误：sid没有传递");
        }
        if (StringUtils.isEmpty(oldPassword)) {
            return ResultBean.errorMsg("请输入旧的密码");
        }
        if (StringUtils.isEmpty(newPassword)) {
            return ResultBean.errorMsg("请输入新的密码");
        }

        User user = service.getUser(sid);
        if (user == null) {
            return ResultBean.errorMsg("会员不存在");
        }
        if (!user.getPassword().equals(MD5Util.inputPass2FormPass(oldPassword))) {
            return ResultBean.errorMsg("旧密码错误，请重新输入");
        }
        if (newPassword.equals(MD5Util.inputPass2FormPass(newPassword))) {
            return ResultBean.errorMsg("新密码和旧密码一样，请重新输入");
        }

        service.changePassword(sid, newPassword);

        return ResultBean.success("修改成功");
    }

}