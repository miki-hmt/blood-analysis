package com.kdgc.dao;

import com.kdgc.common.constant.Rel;
import com.kdgc.entity.ColumnInfo;
import com.kdgc.model.ColMap;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author jczhou
 * @description
 * @date 2020/7/31
 **/
public interface ColumnInfoDao extends Neo4jRepository<ColumnInfo, Long> {

    /**
     * 根据接收方表名查询该表字段映射关系
     * @param url
     * @param tableName
     * @return
     */
    @Query("match p=(n:column_obj)-[r:`"+ Rel.COLUMN_TO_COLUMN +"`]->(m:column_obj{url: $url, tbname: $tableName}) return n as scol,m as rcol")
    List<ColMap> getColMapByRecTable(@Param("url")String url, @Param("tableName")String tableName);
}
