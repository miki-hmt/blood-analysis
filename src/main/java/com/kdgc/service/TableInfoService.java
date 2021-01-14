package com.kdgc.service;

import com.kdgc.common.model.GridPage;
import com.kdgc.entity.ColumnInfo;
import com.kdgc.entity.TableInfo;
import com.kdgc.model.GoJsGraphModel;
import com.kdgc.model.TableLink;
import com.kdgc.model.TableModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author jczhou
 * @description
 * @date 2020/7/20
 **/
public interface TableInfoService {

    /**
     * 根据id获取tableInfo
     * @param id
     * @return
     */
    public TableInfo findById(Long id);

    /**
     * 向下查询
     * @param url
     * @param tableName
     * @return
     */
    public List<TableLink> getChildTableLink(String url, String tableName);

    /**
     * 向上查询
     * @param url
     * @param tableName
     * @return
     */
    public List<TableLink> getParentTableLink(String url, String tableName);

    public List<TableInfo> getAllTableRelByTableName(String tableName, String url);

    public TableModel getTableModel(String tableName, String url);

    public void save(TableInfo tableInfo);

    public void del(TableInfo tableInfo);

    public void saveColumnToColumnRel(ColumnInfo sCol, ColumnInfo rCol);

    public List<TableInfo> findByUrlAndDbTypeAndTableName(String url, String dbType, String tableName);

    /**
     * 保存表与表之间关系
     * @param sTable
     * @param rTable
     */
    public void saveTableRel(TableInfo sTable, TableInfo rTable);

    /**
     * 删除该节点所有表与表关系
     * @param table
     */
    public void delTableRel(TableInfo table);

    /**
     * 删除指定表节点之间的关系
     * @param sTable
     * @param rTable
     */
    public void delTableRel(TableInfo sTable, TableInfo rTable);

    /**
     * 删除指定节点所有关联列之间关系
     * @param table
     */
    public void delTableToColumnRel(TableInfo table);

    /**
     * 删除指定表与指定列关系
     * @param table
     * @param columnInfo
     */
    public void delTableToColumnRel(TableInfo table, ColumnInfo columnInfo);

    public void saveTableToColumnRel(TableInfo table, ColumnInfo columnInfo);

    public GoJsGraphModel getGoJsGraphModel(String tableName, String url);

    public GridPage pageQuery(Pageable pageable);
}
