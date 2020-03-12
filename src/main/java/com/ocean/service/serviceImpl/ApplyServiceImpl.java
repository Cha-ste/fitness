package com.ocean.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ocean.entity.Apply;
import com.ocean.mapper.ApplyMapper;
import com.ocean.mapper.CourseMapper;
import com.ocean.service.ApplyService;
import com.ocean.vo.ApplyVO;
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
    public Apply getApply(Integer cid, Integer sid) {

        Apply model = mapper.getApply(cid, sid);
        if (model == null) {
            logger.error("[getApply]delete Apply cid={}, sid={} fail", cid, sid);
            throw new RuntimeException("GET data fail");
        }
        return model;

    }

    @Override
    @Transactional
    public void save(ApplyVO vo) {
        Apply record = new Apply();
        record.setTid(vo.getTid());
        record.setCid(vo.getCid());
        record.setSid(vo.getSid());
        record.setPunch(vo.getPunch());

        int success = mapper.insert(record);
        // 更新课程的报名人数
        courseMapper.countIncrease(vo.getCid(), vo.getCount() + 1);
        if (success <= 0) {
            logger.error("[addApply]add Apply={} fail", record.toString());
            throw new RuntimeException("Add data fail");
        }
        return;

    }

    @Override
    public void update(Apply model) {
        int success = mapper.updateByPrimaryKey(model);
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
    public void changeTable(Apply apply) {
        int success = mapper.changeTable(apply);
        if (success == 0){
            throw new RuntimeException("报名不存在");
        }
    }
}
