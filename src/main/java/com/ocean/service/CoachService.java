package com.ocean.service;

import java.util.HashMap;
import java.util.List;
import com.ocean.entity.Coach;
import com.github.pagehelper.PageInfo;
import com.ocean.entity.User;


public interface CoachService {

    Coach getCoach(Integer id);

    void save(Coach model);

    void update(Coach model);

    void del(Integer id);

    int getCount();

    /**
     * 根据教练姓名搜索，获取教练列表
     *
     * @param currentPage 当前页
     * @param itemsPerPage 每页记录数
     * @param coachName 教练姓名
     * @return 符合条件的教练列表
     */
    PageInfo<Coach> query(int currentPage, int itemsPerPage, String coachName);
    int queryCount(HashMap<String, Object> paramMap);

    /**
     * 获取发布课程的报名会员列表
     *
     * @param tid 教练id
     * @param cid 课程id
     * @return 学员列表
     */
    List<User> getTraineeList(Integer tid, Integer cid);

    /**
     * 修改教练密码
     *
     * @param tid 教练id
     * @param newPassword 新密码
     */
    void changePassword(Integer tid, String newPassword);

    Coach getCoachForLogin(String coachName, String password);
}