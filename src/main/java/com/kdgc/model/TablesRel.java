package com.kdgc.model;

import com.kdgc.entity.ColumnInfo;
import com.kdgc.entity.TableInfo;
import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.io.Serializable;
import java.util.List;

/**
 * @author jczhou
 * @description
 * @date 2020/7/22
 **/
@QueryResult
@Data
public class TablesRel implements Serializable {

    private TableInfo curTable;

    private List<ColumnInfo> curColumns;

    private List<List<BaseRel>> rel;

    private List<TableInfo> relTables;

    private List<ColumnInfo> relColumns;
}
