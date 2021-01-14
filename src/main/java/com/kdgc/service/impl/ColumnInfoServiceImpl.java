package com.kdgc.service.impl;

import com.kdgc.common.constant.CySql;
import com.kdgc.common.util.Neo4jUtil;
import com.kdgc.dao.ColumnInfoDao;
import com.kdgc.dao.TableInfoDao;
import com.kdgc.entity.ColumnInfo;
import com.kdgc.entity.TableInfo;
import com.kdgc.model.ColMap;
import com.kdgc.service.ColumnInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jczhou
 * @description
 * @date 2020/7/20
 **/
@Service
public class ColumnInfoServiceImpl implements ColumnInfoService {

    @Autowired
    private ColumnInfoDao columnInfoDao;
    @Autowired
    private Neo4jUtil neo4jUtil;
    @Autowired
    private TableInfoDao tableInfoDao;


    @Override
    public void save(ColumnInfo columnInfo) {
        String sql = CySql.getMergeColumnSql(columnInfo);
        neo4jUtil.executeSql(sql);
    }

    @Override
    public void del(ColumnInfo columnInfo) {
        String sql = CySql.getMergeColumnSql(columnInfo);
        neo4jUtil.executeSql(sql);
    }

    @Override
    @Cacheable(value = "GoJsGraphModel", key = "#nodeId")
    public List<ColMap> getColMap(Long nodeId) {
        TableInfo tableInfo = tableInfoDao.findById(nodeId).orElse(null);
        if (tableInfo == null) {
            return null;
        }
        return columnInfoDao.getColMapByRecTable(tableInfo.getUrl(), tableInfo.getTableName());
    }
}
