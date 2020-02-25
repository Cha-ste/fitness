package com.ocean.service;

import java.util.HashMap;
import java.util.List;
import com.ocean.entity.Course;
import com.github.pagehelper.PageInfo;
import com.ocean.vo.CommentVO;
import com.ocean.vo.CourseVO;
import com.ocean.vo.MyCourseVO;


public interface CourseService {

    Course getCourse(Integer id);

    void save(Course model);

    void update(Course model);

    void del(Integer tid, Integer cid);

    int getCount();

    PageInfo<Course> query(int currentPage, int itemsPerPage, HashMap<String, Object> paramMap);
    int queryCount(HashMap<String, Object> paramMap);

    List<CourseVO> getAllCourseList(Integer sid);

    List<MyCourseVO> getMemberCourseList(Integer sid);

    void evaluate(Integer cid, Integer sid, String context);

    List<CommentVO> getCourseEvaluate(Integer cid);

    List<CourseVO> getCoachCourseList(Integer tid);
}