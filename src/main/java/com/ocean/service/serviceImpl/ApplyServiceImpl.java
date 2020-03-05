package com.ocean.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ocean.entity.Apply;
import com.ocean.mapper.ApplyMapper;
import com.ocean.mapper.CourseMapper;
import com.ocean.service.ApplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
public class ApplyServiceImpl implements ApplyService {

    private static final Logger logger = LoggerFactory.getLogger(ApplyServiceImpl.class);

    @Autowired
    private ApplyMapper mapper;
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public Apply getApply(Integer id) {

        Apply model = mapper.selectByPrimaryKey(id);
        if (model == null) {
            logger.error("[getApply]delete Apply id={} fail", id);
            throw new RuntimeException("GET data fail");
        }
        return model;

    }

    @Override
    @Transactional
    public void save(Apply model) {
        int success = mapper.insert(model);
        // 更新课程的报名人数
        courseMapper.countIncrease(model.getCid());
        if (success <= 0) {
            logger.error("[addApply]add Apply={} fail", model.toString());
            throw new RuntimeException("Add data fail");
        }
        return;

    }

    @Override
    public void update(Apply model) {
        int success = mapper.updateByPrimaryKeySelective(model);
        if (success <= 0) {
            logger.error("[updateApply]update Apply={} fail", model.toString());
            throw new RuntimeException("Modify data fail");
        }
        return;

    }

    @Override
    public void del(Integer cid, Integer sid) {

        int success = mapper.delete(cid, sid);
        if (success <= 0) {
            logger.error("[deleteApply]delete Apply cid={}, sid={} fail", cid, sid);
            throw new RuntimeException("Del data fail");
        }
        return;

    }

    @Override
    public PageInfo<Apply> query(int pageNum, int pageSize, HashMap<String, Object> paramMap) {
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

}
