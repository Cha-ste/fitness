package com.ocean.mapper;

import com.ocean.entity.Course;
import com.ocean.vo.CommentVO;
import com.ocean.vo.CourseVO;
import com.ocean.vo.MyCourseVO;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface CourseMapper {

    int deleteByPrimaryKey(String id);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

    int getCount();

    List<Course> query(HashMap<String, Object> paramMap);
    int queryCount(HashMap<String, Object> paramMap);

    List<CourseVO> getAllCourseList(@Param("sid")Integer sid,
                                    @Param("keyword")String keyword);

    List<MyCourseVO> getMemberCourseList(Integer sid);

    /**
     * 会员评论课程
     *
     * @param cid 课程id
     * @param sid 会员id
     * @param context 评论内容
     */
    void evaluate(@Param("cid")Integer cid, @Param("sid")Integer sid,
                  @Param("context")String context);

    /**
     * 获取课程的全部评论
     *
     * @param cid 课程id
     * @return 评论列表
     */
    List<CommentVO> getCourseEvaluate(Integer cid);

    /**
     * 获取教练发布的课程列表
     *
     * @param tid 教练id
     * @return 课程列表
     */
    List<CourseVO> getCoachCourseList(Integer tid);

    int deleteCoachCourse(@Param("tid") Integer tid,
                          @Param("cid") Integer cid);
}