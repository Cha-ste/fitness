package com.ocean.controller;

import com.github.pagehelper.PageInfo;
import com.ocean.entity.User;
import com.ocean.service.UserService;
import com.ocean.utils.MD5Util;
import com.ocean.utils.TokenUtils;
import com.ocean.vo.CoachLoginVO;
import com.ocean.vo.MemberLoginVO;
import com.ocean.vo.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController("UserController")
@RequestMapping("/user")
@Api(value = "会员", tags = "会员相关接口")
public class UserController {

    public static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService service;

    @PostMapping(value = "/register")
    @ApiOperation(value = "会员注册")
    public ResultBean<String> register(@RequestBody @Validated User user) {
        if (service.usernameExist(user.getUsername())) {
            ResultBean.errorMsg("用户名已经存在");
        }

        service.save(user);
        return ResultBean.success("注册成功");
    }

    @PostMapping(value = "/login")
    @ApiOperation(value = "会员登录")
    public ResultBean<Map<String, Object>> login(@RequestBody @Validated MemberLoginVO vo) {
        Map<String, Object> result = new HashMap<>();
        User user = new User();
        user.setUsername(vo.getUsername());
        user.setPassword(vo.getPassword());
        User member = service.getUserForLogin(user);
        if (member == null) {
            return ResultBean.errorMsg("账号密码不正确");
        }
        member.setPassword(null);
        result.put("user", member);

        Map<String, Object> playLoad = new HashMap<>();
        Date date = new Date();
        playLoad.put("id", member.getSid());
        playLoad.put("ct", date.getTime());
        playLoad.put("ext", date.getTime() + 1000*60*120); //设置两个小时过期
        result.put("token", TokenUtils.createToken(playLoad));
        return ResultBean.success(result);
    }


    @GetMapping(value = "/getUserInfo")
    @ApiOperation("获取会员信息")
    public ResultBean<User> getUserInfo(@RequestParam("sid") Integer sid) {
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
    public ResultBean del(@RequestParam Integer sid) {
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
    public ResultBean changePassword(@RequestParam Integer sid,
                                     @RequestParam String oldPassword,
                                     @RequestParam String newPassword) {
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