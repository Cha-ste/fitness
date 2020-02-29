package com.ocean.controller;

import com.github.pagehelper.PageInfo;
import com.ocean.entity.Coach;
import com.ocean.entity.User;
import com.ocean.service.CoachService;
import com.ocean.utils.MD5Util;
import com.ocean.utils.TokenUtils;
import com.ocean.vo.CoachLoginVO;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("CoachController")
@RequestMapping("/coach")
@Api(value = "教练", tags = "教练相关接口")
public class CoachController {

    public static Logger logger = LoggerFactory.getLogger(CoachController.class);

    @Autowired
    private CoachService service;

    @PostMapping(value = "/login")
    @ApiOperation("教练登录")
    public ResultBean<Map<String, Object>> login(@RequestBody @Validated CoachLoginVO vo) {
        Coach coach = service.getCoachForLogin(vo.getCoachName(), vo.getPassword());
        if (coach != null) {
            coach.setPassword(null);
            Map<String, Object> result = new HashMap<>();
            result.put("coach", coach);

            // 登录成功，返回token
            Map<String, Object> playLoad = new HashMap<>();
            Date date = new Date();
            playLoad.put("id", coach.getTid());
            playLoad.put("ct", date.getTime());
            playLoad.put("ext", date.getTime() + 1000*60*120); //设置两个小时过期
            result.put("token", TokenUtils.createToken(playLoad));

            return ResultBean.success(result);
        } else {
            return ResultBean.errorMsg("教练不存在或者密码错误");
        }
    }

    @GetMapping(value = "/traineeList")
    @ApiOperation("获取已发布课程的报名会员列表")
    public ResultBean<List<User>> get(@RequestParam Integer tid,
                                      @RequestParam Integer cid) {
        try {
            List<User> memberList = service.getTraineeList(tid, cid);
            return ResultBean.success(memberList);
        } catch (Exception e) {
            logger.error("Fail:", e);
            return ResultBean.ERROR;
        }
    }

    @GetMapping(value = "/get")
    @ApiOperation("获取教练信息")
    public ResultBean<Coach> get(@RequestParam("tid") Integer tid) {
        if (com.ocean.utils.StringUtils.isNullOrZero(tid)) {
            return ResultBean.errorMsg("参数错误：tid没有传递");
        }
        try {
            Coach entity = service.getCoach(tid);
            entity.setPassword(null); // 隐藏密码
            return ResultBean.success(entity);
        } catch (Exception e) {
            logger.error("Fail:", e);
            return ResultBean.errorMsg("教练不存在");
        }
    }

    @GetMapping(value = "/getCoachList")
    @ResponseBody
    @ApiOperation("获取教练列表（带分页），可根据教练名字搜索")
    public ResultBean<PageInfo<Coach>> getCoachList(
            @RequestParam(required = false) Integer pageNum,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String coachName) {
        if (pageNum == null || pageNum.equals(0)) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize.equals(0)) {
            pageSize = 20;
        }
        if (StringUtils.isEmpty(coachName)) {
            coachName = null;
        }
        PageInfo<Coach> pageInfo = service.query(pageNum, pageSize, coachName);
        return ResultBean.success(pageInfo);
    }

    @PostMapping(value = "/save")
    @ApiOperation("新增或者修改教练信息（tid为空的时候新增，tid不空时为修改）")
    public ResultBean save(@RequestBody Coach model) {
        if (StringUtils.isEmpty(model.getCoachName())) {
            return ResultBean.errorMsg("教练名字不能为空");
        }
        if (StringUtils.isEmpty(model.getPassword())) {
            return ResultBean.errorMsg("教练密码不能为空");
        }
        if (service.coachNameExist(model.getCoachName())) {
            return ResultBean.errorMsg("名字已存在");
        }

        try {
            Coach record = new Coach();
            BeanUtils.copyProperties(model, record);

            if (record.getTid() == null || record.getTid().equals(0)) {
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
    @ApiOperation("删除教练")
    public ResultBean del(@RequestParam Integer tid) {
        try {
            service.del(tid);
        } catch (Exception e) {
            logger.error("Fail:", e);
            return ResultBean.errorMsg("教练不存在，删除失败");
        }
        return ResultBean.success("删除成功");
    }

    @PostMapping(value = "/changePassword")
    @ApiOperation("修改教练密码")
    public ResultBean changePassword(@RequestParam Integer tid,
                                     @RequestParam String oldPassword,
                                     @RequestParam String newPassword) {
        Coach coach = service.getCoach(tid);
        if (coach == null) {
            return ResultBean.errorMsg("教练不存在");
        }
        if (!coach.getPassword().equals(MD5Util.inputPass2FormPass(oldPassword))) {
            return ResultBean.errorMsg("旧密码错误，请重新输入");
        }
        if (coach.getPassword().equals(MD5Util.inputPass2FormPass(newPassword))) {
            return ResultBean.errorMsg("新密码和旧密码一样，请重新输入");
        }

        service.changePassword(tid, newPassword);

        return ResultBean.success("修改成功");
    }

}