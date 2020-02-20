package com.ocean.service;

import com.github.pagehelper.PageInfo;
import com.ocean.entity.User;

import java.util.HashMap;


public interface UserService {

    User getUser(Integer id);

    void save(User model);

    void update(User model);

    void del(Integer id);

    int getCount();

    PageInfo<User> query(int currentPage, int itemsPerPage, String username);
    int queryCount(HashMap<String, Object> paramMap);

    public boolean usernameExist(String username);

    User getUserForLogin(User user);

    void changePassword(Integer sid, String newPassword);
}