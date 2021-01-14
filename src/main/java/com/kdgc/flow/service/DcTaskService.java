package com.kdgc.flow.service;

import com.kdgc.flow.dao.DcTaskDao;
import com.kdgc.flow.entity.DcwebTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jczhou
 * @description
 * @date 2020/7/31
 **/
@Service
public class DcTaskService {

    @Autowired
    private DcTaskDao dcTaskDao;


    public List<DcwebTask> findByFlowCode(String flowCode) {
        return dcTaskDao.findByFlowCode(flowCode);
    }
}
