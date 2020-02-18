package com.ocean.service.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;

import com.ocean.entity.User;
import com.ocean.service.UserService;
import com.ocean.mapper.primary.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class UserServiceImpl implements UserService{

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper mapper;

    @Override
    public User getUser(String id) {

        User model = mapper.selectByPrimaryKey(Integer.valueOf(id));
        if (model == null) {
            logger.error("[getUser]delete User id={} fail", id);
            throw new RuntimeException("GET data fail");
        }
        return model;

    }

    @Override
    public void save(User model) {
        int success = mapper.insertSelective(model);
        if (success <= 0) {
            logger.error("[addUser]add User={} fail",  model.toString());
            throw new RuntimeException("Add data fail");
        }
        return;

    }

    @Override
    public void update(User model) {
        int success = mapper.updateByPrimaryKeySelective(model);
        if (success <= 0) {
            logger.error("[updateUser]update User={} fail",  model.toString());
            throw new RuntimeException("Modify data fail");
        }
        return;

    }

    @Override
    public void del(String id) {

        int success = mapper.deleteByPrimaryKey(id);
        if (success <= 0) {
            logger.error("[deleteUser]delete User id={} fail", id);
            throw new RuntimeException("Del data fail");
        }
        return;

    }

    @Override
    public PageInfo<User> query(int pageNum, int pageSize, HashMap<String, Object> paramMap) {
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
    public User getUserByMobile(String mobile) {
        return mapper.getUserByMobile(mobile);
    }
}