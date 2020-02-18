package com.ocean.service;

import java.util.HashMap;
import java.util.List;
import com.ocean.entity.User;
import com.github.pagehelper.PageInfo;


public interface UserService {

    User getUser(String id);

    void save(User model);

    void update(User model);

    void del(String id);

    int getCount();

    PageInfo<User> query(int currentPage, int itemsPerPage, HashMap<String, Object> paramMap);
    int queryCount(HashMap<String, Object> paramMap);

    User getUserByMobile(String mobile);
}