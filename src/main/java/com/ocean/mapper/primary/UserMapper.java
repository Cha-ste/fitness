package com.ocean.mapper.primary;

import com.ocean.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

public interface UserMapper {

    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int getCount();

    @Select("select * from users where mobile = #{mobile}")
    User getUserByMobile(String mobile);

    List<User> query(HashMap<String, Object> paramMap);
    int queryCount(HashMap<String, Object> paramMap);

}