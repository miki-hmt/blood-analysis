package com.kdgc.entity;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * @author jczhou
 * @description
 * @date 2020/7/20
 **/
@NodeEntity(label = "column_obj")
@Data
public class ColumnInfo {

    @Id
    @GeneratedValue
    private Long id;

    @Property("name")
    private String colName;

    @Property("comment")
    private String comment;

    @Property("coltype")
    private String colType;

    @Property("url")
    private String url;

    @Property("tbname")
    private String tableName;
}
