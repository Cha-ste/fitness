package com.ocean.mapper;

import com.ocean.entity.Coach;
import com.ocean.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface CoachMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Coach record);

    int insertSelective(Coach record);

    int updateByPrimaryKey(Coach record);

    int getCount();

    List<Coach> query(@Param("coachName") String coachName);

    int queryCount(HashMap<String, Object> paramMap);

    Coach getCoach(Integer id);

    List<User> getTraineeList(Integer tid, Integer cid);

    void changePassword(@Param("tid") Integer tid,
                        @Param("newPassword") String newPassword);

    Coach getCoachForLogin(@Param("coachName")String coachName,
                           @Param("password")String password);

    int coachNameExist(String coachName);
}