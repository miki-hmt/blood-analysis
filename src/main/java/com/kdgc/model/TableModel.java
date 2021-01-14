package com.kdgc.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author jczhou
 * @description
 * @date 2020/7/22
 **/
@Setter
@Getter
@Builder
public class TableModel {

    private Long id;

    private String tableName;

    private List<ColumnModel> columns;

    private List<TableModel> srcTables;

}
