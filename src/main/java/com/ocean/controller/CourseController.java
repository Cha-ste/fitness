package com.ocean.controller;

import com.ocean.entity.Course;
import com.ocean.service.CourseService;
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

import java.util.List;

@RestController("CourseController")
@RequestMapping("/course")
@Api(value = "健身课程", tags = "课程相关接口")
public class CourseController {

    public static Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService service;

    @GetMapping(value = "/get")
    @ApiOperation("获取课程信息")
    public ResultBean<Course> get(@RequestParam Integer id) {
        try {
            Course entity = service.getCourse(id);
            return ResultBean.success(entity);
        } catch (Exception e) {
            logger.error("Fail:", e);
            return ResultBean.ERROR;
        }
    }

    @PostMapping(value = "/getCourseEvaluate")
    @ResponseBody
    @ApiOperation(value = "课程的评论")
    public ResultBean<List<CommentVO>> getCourseEvaluate(@RequestParam Integer cid) {
        List<CommentVO> commentList = service.getCourseEvaluate(cid);

        return ResultBean.success(commentList);
    }

    @PostMapping(value = "/evaluate")
    @ResponseBody
    @ApiOperation(value = "会员评论已学课程")
    public ResultBean<String> evaluate(@RequestParam Integer cid,
                                       @RequestParam Integer sid,
                                       @RequestParam String context) {
        service.evaluate(cid, sid, context);

        return ResultBean.success("评论成功");
    }

    @GetMapping(value = "/getMemberCourseList")
    @ResponseBody
    @ApiOperation(value = "获取会员已报名课程列表")
    public ResultBean<List<MyCourseVO>> getMemberCourseList(@RequestParam Integer sid) {
        List<MyCourseVO> courseList = service.getMemberCourseList(sid);

        return ResultBean.success(courseList);
    }

    @GetMapping(value = "/getCoachCourseList")
    @ResponseBody
    @ApiOperation(value = "获取教练发布课程列表")
    public ResultBean<List<CourseVO>> getCoachCourseList(@RequestParam Integer tid) {
        List<CourseVO> courseList = service.getCoachCourseList(tid);

        return ResultBean.success(courseList);
    }

    @GetMapping(value = "/getAllCourseList")
    @ResponseBody
    @ApiOperation(value = "获取所有课程列表")
    public ResultBean<List<CourseVO>> getAllCourseList(@RequestParam Integer sid) {
        List<CourseVO> courseList = service.getAllCourseList(sid);

        return ResultBean.success(courseList);
    }

    @PostMapping(value = "/save")
    @ApiOperation("教练添加或修改课程（cid为空的时候新增，cid不空时为修改）")
    public ResultBean save(@RequestBody @Validated Course model) {
        try {
            Course record = new Course();
            BeanUtils.copyProperties(model, record);

            if (record.getCid() == null || record.getCid().equals(0)) {
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
    @ApiOperation("删除教练发布的课程")
    public ResultBean del(@RequestBody CourseDelVo vo) {
        try {
            service.del(vo.getTid(), vo.getCid());
        } catch (Exception e) {
            logger.error("Fail:", e);
            return ResultBean.errorMsg("删除失败：课程不存在，或者课程不是该教练所发布");
        }
        return ResultBean.success("删除成功");
    }

}