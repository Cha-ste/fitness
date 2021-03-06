package com.ocean.controller;

import com.github.pagehelper.PageInfo;
import com.ocean.entity.User;
import com.ocean.service.CoachService;
import com.ocean.service.UserService;
import com.ocean.utils.MD5Util;
import com.ocean.utils.TokenUtils;
import com.ocean.vo.*;
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
    @Autowired
    private CoachService coachService;

    @PostMapping(value = "/changePasswordByManager")
    @ApiOperation(value = "管理员修改别人密码")
    public ResultBean<String> changePasswordByManager(
            @Validated ManagerChangePasswordVo vo) {
        if(!"sadministrator".equals(vo.getManagerName())
                || !"sadministrator".equals(vo.getManagerPassword())) {
            return ResultBean.errorMsg("管理员账号密码错误");
        }

        try {
            if ("coach".equals(vo.getUserType())) {
                coachService.changePassword(vo.getId(), vo.getNewPassword());
            } else if("member".equals(vo.getUserType())) {
                service.changePassword(vo.getId(), vo.getNewPassword());
            } else {
                return ResultBean.errorMsg("用户类型错误");
            }
        } catch (RuntimeException e) {
            return ResultBean.errorMsg("用户不存在，密码修改失败");
        }

        return ResultBean.success("修改成功");
    }

    @PostMapping(value = "/register")
    @ApiOperation(value = "会员注册")
    public ResultBean<String> register(@Validated User user) {
        if (service.usernameExist(user.getUsername())) {
            return ResultBean.errorMsg("用户名已经存在");
        }

        service.save(user);
        return ResultBean.success("注册成功");
    }

    @PostMapping(value = "/login")
    @ApiOperation(value = "会员登录")
    public ResultBean<Map<String, Object>> login(@Validated MemberLoginVO vo) {
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
        playLoad.put("ext", date.getTime() + 1000*60*60*120); //设置两个小时过期
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
            return ResultBean.errorMsg("数据不存在");
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
    public ResultBean save(User model) {
        try {
            if (com.ocean.utils.StringUtils.isNullOrZero(model.getSid())) {
                return ResultBean.errorMsg("参数错误：sid没有传递");
            } else {
                service.update(model);
                return ResultBean.success("保存成功");
            }
        } catch (Exception e) {
            logger.error("Fail:", e);
            return ResultBean.ERROR;
        }
    }

    @PostMapping(value = "/del")
    @ApiOperation("删除会员")
    public ResultBean del(Integer sid) {
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
    public ResultBean changePassword(MemberChangePasswordVo vo) {
        User user = service.getUser(vo.getSid());
        if (user == null) {
            return ResultBean.errorMsg("会员不存在");
        }
        if (!user.getPassword().equals(MD5Util.inputPass2FormPass(vo.getOldPassword()))) {
            return ResultBean.errorMsg("旧密码错误，请重新输入");
        }
        if (user.getPassword().equals(MD5Util.inputPass2FormPass(vo.getNewPassword()))) {
            return ResultBean.errorMsg("新密码和旧密码一样，请重新输入");
        }

        service.changePassword(vo.getSid(), vo.getNewPassword());

        return ResultBean.success("修改成功");
    }

}
