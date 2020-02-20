package com.ocean.service;

import java.util.HashMap;
import java.util.List;
import com.ocean.entity.Course;
import com.github.pagehelper.PageInfo;
import com.ocean.vo.CommentVo;
import com.ocean.vo.CourseVo;
import com.ocean.vo.MyCourseVo;
import io.swagger.models.auth.In;


public interface CourseService {

    Course getCourse(Integer id);

    void save(Course model);

    void update(Course model);

    void del(Integer tid, Integer cid);

    int getCount();

    PageInfo<Course> query(int currentPage, int itemsPerPage, HashMap<String, Object> paramMap);
    int queryCount(HashMap<String, Object> paramMap);

    List<CourseVo> getAllCourseList(Integer sid);

    List<MyCourseVo> getMemberCourseList(Integer sid);

    void evaluate(Integer cid, Integer sid, String context);

    List<CommentVo> getCourseEvaluate(Integer cid);

    List<CourseVo> getCoachCourseList(Integer tid);
}