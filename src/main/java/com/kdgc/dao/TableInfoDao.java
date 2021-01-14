package com.kdgc.dao;

import com.kdgc.common.constant.Rel;
import com.kdgc.entity.TableInfo;
import com.kdgc.model.TableLink;
import com.kdgc.model.TablesRel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * @author jczhou
 * @description
 * @date 2020/7/20
 **/
public interface TableInfoDao extends Neo4jRepository<TableInfo, Long> {


    public List<TableInfo> findByTableName(String tableName);

    /**
     * 向下查询表连接
     * @param tableName
     * @return
     */
    @Query("match (n:table_obj) -[r:`"+ Rel.TABLE_TO_TABLE +"`]->(m:table_obj{url: $url, name: $tableName}) return n as stable,m as rtable")
    public List<TableLink> getParentTableLink(@Param("url")String url, @Param("tableName")String tableName);

    /**
     * 向上查询表连接
     * @param tableName
     * @return
     */
    @Query("match (n:table_obj{url: $url, name: $tableName}) -[r:`"+ Rel.TABLE_TO_TABLE +"`]->(m:table_obj) return n as stable,m as rtable")
    public List<TableLink> getChildTableLink(@Param("url")String url, @Param("tableName")String tableName);

    /**
     * 获取相关联所有节点
     * @param tableName
     * @param url
     * @return
     */
    @Query("match (c:column_obj)<-[r1:`"+ Rel.TABLE_TO_COLUMN +"`]-(n:table_obj{name: $tableName, url: $url})" +
            "-[r:`"+ Rel.TABLE_TO_TABLE +"`*1.."+ Rel.MAX_REL_DEEP+"]-(m)-[r2:`"+ Rel.TABLE_TO_COLUMN +"`]->(d:column_obj) " +
            "return collect(distinct c) as curColumns,n as curTable," +
            "collect(distinct r) as rel,collect(distinct m) as relTables,collect(distinct d) as relColumns")
    public List<TablesRel> getAllTableRelByTableName(@Param("tableName")String tableName, @Param("url")String url);

    public List<TableInfo> findByUrlAndDbTypeAndTableName(String url, String dbType, String tableName);


    @Query(value = "match(n:table_obj) return n", countQuery = "match(n:table_obj) return count(n)")
    public Page<Map> pageQuery(Pageable pageable);
}
