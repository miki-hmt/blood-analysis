package com.kdgc.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kdgc.common.constant.Rel;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

import java.util.List;

/**
 * @author jczhou
 * @description
 * @date 2020/7/20
 **/
@NodeEntity(label = "table_obj")
@Data
public class TableInfo {

    @Id
    @GeneratedValue
    private Long id;

    @Property("name")
    private String tableName;

    /**
     * 数据库连接 如:192.168.62.20:3306/dcweb
     */
    @Property("url")
    private String url;

    /**
     * 数据库类型 如: mysql
     */
    @Property("dbType")
    private String dbType;

    @Property("srType")
    private String srType;

    @Relationship(type = Rel.TABLE_TO_COLUMN)
    @JsonProperty("columns")
    private List<ColumnInfo> columns;


    @Relationship(type = Rel.TABLE_TO_TABLE)
    @JsonProperty("tables")
    private List<TableInfo> tables;

}
