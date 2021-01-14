package com.kdgc.flow.dao;

import com.kdgc.flow.entity.DcwebTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author jczhou
 * @description
 * @date 2020/7/31
 **/
public interface DcTaskDao extends JpaRepository<DcwebTask, Long> {


    /**
     * 根据flowCode获取流程信息
     * @param flowCode
     * @return
     */
    public List<DcwebTask> findByFlowCode(String flowCode);
}
