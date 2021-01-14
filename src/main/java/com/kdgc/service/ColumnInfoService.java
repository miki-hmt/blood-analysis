package com.kdgc.service;

import com.kdgc.entity.ColumnInfo;
import com.kdgc.model.ColMap;

import java.util.List;

/**
 * @author jczhou
 * @description
 * @date 2020/7/20
 **/
public interface ColumnInfoService {

    /**
     * 保持列信息
     * @param columnInfo
     */
    public void save(ColumnInfo columnInfo);

    /**
     * 删除列信息
     * @param columnInfo
     */
    public void del(ColumnInfo columnInfo);

    /**
     * 获取列映射关系
     * @param nodeId
     * @return
     */
    public List<ColMap> getColMap(Long nodeId);
}
