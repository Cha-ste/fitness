package com.ocean.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ocean.entity.Course;
import com.ocean.mapper.CourseMapper;
import com.ocean.service.CourseService;
import com.ocean.vo.CommentVo;
import com.ocean.vo.CourseVo;
import com.ocean.vo.MyCourseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService{

    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Autowired
    private CourseMapper mapper;

    @Override
    public Course getCourse(Integer id) {

        Course model = mapper.selectByPrimaryKey(id);
        if (model == null) {
            logger.error("[getCourse]delete Course id={} fail", id);
            throw new RuntimeException("GET data fail");
        }
        return model;

    }

    @Override
    public void save(Course model) {
        int success = mapper.insert(model);
        if (success <= 0) {
            logger.error("[addCourse]add Course={} fail",  model.toString());
            throw new RuntimeException("Add data fail");
        }
        return;

    }

    @Override
    public void update(Course model) {
        int success = mapper.updateByPrimaryKeySelective(model);
        if (success <= 0) {
            logger.error("[updateCourse]update Course={} fail",  model.toString());
            throw new RuntimeException("Modify data fail");
        }
        return;

    }

    @Override
    public void del(Integer tid, Integer cid) {

        int success = mapper.deleteCoachCourse(tid, cid);
        if (success <= 0) {
            throw new RuntimeException("删除失败：课程不存在，或者课程不是该教练所发布");
        }
        return;

    }

    @Override
    public PageInfo<Course> query(int pageNum, int pageSize, HashMap<String, Object> paramMap) {
        PageHelper.startPage(pageNum, pageSize);

        return new PageInfo<>(mapper.query(paramMap));
    }

    @Override
    public int getCount() {
        return mapper.getCount();
    }

    @Override
    public int queryCount(HashMap<String, Object> paramMap) {
        return mapper.queryCount(paramMap);
    }

    @Override
    public List<CourseVo> getAllCourseList(Integer sid) {
        return mapper.getAllCourseList(sid);
    }

    @Override
    public List<MyCourseVo> getMemberCourseList(Integer sid) {
        return mapper.getMemberCourseList(sid);
    }

    @Override
    public void evaluate(Integer cid, Integer sid, String context) {
        mapper.evaluate(cid, sid, context);
    }

    @Override
    public List<CommentVo> getCourseEvaluate(Integer cid) {
        return mapper.getCourseEvaluate(cid);
    }

    @Override
    public List<CourseVo> getCoachCourseList(Integer tid) {
        return mapper.getCoachCourseList(tid);
    }
}