package com.ocean.mapper;

import com.ocean.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface UserMapper {

    int deleteByPrimaryKey(Integer sid);

    int insert(User record);

    User getUser(Integer sid);

    int updateByPrimaryKey(User record);

    int getCount();

    List<User> query(@Param("username")String username);
    int queryCount(HashMap<String, Object> paramMap);

    User getByUsername(String username);

    User getUserForLogin(@Param("username") String username,
                         @Param("password")String password);

    int changePassword(@Param("sid")Integer sid, @Param("newPassword")String newPassword);
}