package com.kdgc.service.impl;

import com.kdgc.common.constant.Color;
import com.kdgc.common.constant.CySql;
import com.kdgc.common.constant.Rel;
import com.kdgc.common.enums.SourceType;
import com.kdgc.common.model.GridPage;
import com.kdgc.common.util.Neo4jUtil;
import com.kdgc.dao.TableInfoDao;
import com.kdgc.entity.ColumnInfo;
import com.kdgc.entity.TableInfo;
import com.kdgc.model.*;
import com.kdgc.service.TableInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author jczhou
 * @description
 * @date 2020/7/20
 **/
@Service
public class TableInfoServiceImpl implements TableInfoService {


    @Autowired
    private Neo4jUtil neo4jUtil;

    @Autowired
    private TableInfoDao tableInfoDao;


    @Override
    public TableInfo findById(Long id) {
        return tableInfoDao.findById(id).orElse(null);
    }

    @Override
    public List<TableLink> getChildTableLink(String url, String tableName) {
        return tableInfoDao.getChildTableLink(url, tableName);
    }

    @Override
    public List<TableLink> getParentTableLink(String url, String tableName) {
        return tableInfoDao.getParentTableLink(url, tableName);
    }

    /**
     * 获取相关tree集合
     * @param tableName
     * @return
     */
    @Override
    public List<TableInfo> getAllTableRelByTableName(String tableName, String url) {
        List<TableInfo> result = new ArrayList<>();
        List<TablesRel> tableRels = tableInfoDao.getAllTableRelByTableName(tableName, url);
        if (tableRels != null && tableRels.size() > 0) {
            tableRels.forEach(rel ->{
                rel.getCurTable().setColumns(rel.getCurColumns());
                result.add(rel.getCurTable());

                if (rel.getRelTables() != null && rel.getRelTables().size() > 0) {
                    rel.getRelTables().forEach(tab->{
                        String tbName = tab.getTableName() == null ? "" : tab.getTableName();
                        List<ColumnInfo> cols = rel.getRelColumns().stream().filter(c->tbName.equals(c.getTableName())).collect(Collectors.toList());
                        tab.setColumns(cols);
                        result.add(tab);
                    });
                }
            });

        }

        return result;
    }

    /**
     * 转成xm前端展示的json对象
     * @param tableName
     * @return
     */
    @Override
    public TableModel getTableModel(String tableName, String url) {
        return this.tableInfoToTableModel(this.getTableInfoMaxDepthTree(tableName, url));
    }

    public List<TableInfo> getTableInfoMaxDepthTree(String tableName, String url) {
        List<TableInfo> tableInfos = this.getAllTableRelByTableName(tableName, url);
        if (tableInfos == null || tableInfos.size() == 0) {
            return null;
        }
        int[] deepArr = new int[tableInfos.size()];
        for (int i = 0, num = tableInfos.size(); i < num; i++) {
            deepArr[i] = this.maxDepth(tableInfos.get(i));
        }

        List<TableInfo> result = new ArrayList<>();
        int maxValue = deepArr[this.getMaxArrIndex(deepArr)];
        for (int i = 0; i < deepArr.length; i++) {
            if (maxValue == deepArr[i]) {
                result.add(tableInfos.get(i));
            }
        }

        return result;
    }

    /**
     * TableInfo 转 TableModel
     * @param tableInfos
     * @return
     */
    private TableModel tableInfoToTableModel(List<TableInfo> tableInfos) {
        if (tableInfos != null && tableInfos.size() > 0) {
            TableInfo tableInfo = tableInfos.get(0);
            TableModel tableModel = TableModel.builder()
                    .id(tableInfo.getId())
                    .tableName(tableInfo.getTableName())
                    .columns(this.columnInfoToColumnModel(tableInfo.getColumns()))
                    .build();
            if (tableInfo.getTables() != null && tableInfo.getTables().size() > 0) {
                List<TableModel> tableModels = new ArrayList<>(tableInfo.getTables().size());
                tableInfo.getTables().forEach(tab->{
                    List<TableInfo> tabs = new ArrayList<>();
                    tabs.add(tab);
                    tableModels.add(this.tableInfoToTableModel(tabs));
                });
                tableModel.setSrcTables(tableModels);
            }

            return tableModel;
        } else {
            return null;
        }

    }

    /**
     * ColumnInfo 转 ColumnModel
     * @param columnInfos
     * @return
     */
    private List<ColumnModel> columnInfoToColumnModel(List<ColumnInfo> columnInfos) {
        List<ColumnModel> columnModels = new ArrayList<>(columnInfos.size());
        columnInfos.forEach(col->{
            columnModels.add(ColumnModel.builder()
                    .columnName(col.getColName())
                    .comment(col.getComment())
                    .type(col.getColType())
                    .build());
        });
        return columnModels;
    }

    /**
     * 获取tree最大深度
     * @param root
     * @return
     */
    private int maxDepth(TableInfo root) {
        if (root == null) {
            return 0;
        } else if (root.getTables() == null) {
            return 1;
        } else {
            List<Integer> heights = new LinkedList<>();
            for (TableInfo item : root.getTables()) {
                heights.add(maxDepth(item));
            }
            return Collections.max(heights) + 1;
        }
    }

    /**
     * 获取数组最大值下标
     * @param arr
     * @return
     */
    private int getMaxArrIndex(int[] arr) {
        int max = 0;
        int maxIndex = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    @Override
    public void save(TableInfo tableInfo) {
        String sql = CySql.getMergeTableSql(tableInfo);
        neo4jUtil.executeSql(sql);
    }

    @Override
    public List<TableInfo> findByUrlAndDbTypeAndTableName(String url, String dbType, String tableName) {
        return tableInfoDao.findByUrlAndDbTypeAndTableName(url, dbType, tableName);
    }

    public boolean isExitTable(TableInfo tableInfo) {
        List<TableInfo> list = tableInfoDao.findByUrlAndDbTypeAndTableName(tableInfo.getUrl(), tableInfo.getDbType(), tableInfo.getTableName());
        if (list == null || list.size() == 0) {
            return false;
        } else {
            tableInfo.setId(list.get(0).getId());
            return true;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(TableInfo tableInfo) {
        String sql = CySql.getDeleteTableSql(tableInfo);
        neo4jUtil.executeSql(sql);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTableRel(TableInfo sTable, TableInfo rTable) {
        String sql = CySql.getMergeTableToTableSql(sTable, rTable);
        neo4jUtil.executeSql(sql);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delTableRel(TableInfo table) {
        String sql = CySql.getDeleteRelSql(Rel.TABLE_TO_TABLE, CySql.getLinkNodeStr(table.getUrl(), table.getTableName()));
        neo4jUtil.executeSql(sql);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delTableRel(TableInfo sTable, TableInfo rTable) {
        String sql = CySql.getDeleteRelSql(Rel.TABLE_TO_TABLE, CySql.getLinkNodeStr(sTable.getUrl(), sTable.getTableName()), CySql.getLinkNodeStr(rTable.getUrl(), rTable.getTableName()));
        neo4jUtil.executeSql(sql);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delTableToColumnRel(TableInfo table) {
        String sql = CySql.getDeleteRelSql(Rel.TABLE_TO_COLUMN, CySql.getLinkNodeStr(table.getUrl(), table.getTableName()));
        neo4jUtil.executeSql(sql);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delTableToColumnRel(TableInfo table, ColumnInfo columnInfo) {
        String sql = CySql.getDeleteRelSql(Rel.TABLE_TO_COLUMN, CySql.getLinkNodeStr(table.getUrl(), table.getTableName()), CySql.getLinkNodeStr(table.getUrl(), table.getTableName(), columnInfo.getColName()));
        neo4jUtil.executeSql(sql);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTableToColumnRel(TableInfo table, ColumnInfo columnInfo) {
        String sql = CySql.getMergeTableToColumnSql(table, columnInfo.getColName());
        neo4jUtil.executeSql(sql);
    }

    @Override
    public void saveColumnToColumnRel(ColumnInfo sCol, ColumnInfo rCol) {
        String sql = CySql.getMergeTableToColumn(sCol, rCol);
        neo4jUtil.executeSql(sql);
    }

    @Override
    @Cacheable(value = "GoJsGraphModel", key = "#url+'-'+#tableName")
    public GoJsGraphModel getGoJsGraphModel(String tableName, String url) {
        return this.tableInfoToGoJsGraphModel(this.getTableInfoMaxDepthTree(tableName, url));
    }

    public GoJsGraphModel tableInfoToGoJsGraphModel(List<TableInfo> tableInfos) {
        if (tableInfos != null && tableInfos.size() > 0) {
            List<GoJsNode> goJsNodes = new ArrayList<>();
            List<GoJsLink> goJsLinks = new ArrayList<>();
            tableInfos.forEach(tableInfo -> {
                goJsNodes.add(GoJsNode.builder()
                        .nodeId(tableInfo.getId())
                        .srType("[" + SourceType.getNameByType(tableInfo.getSrType()) + "]")
                        .key(tableInfo.getTableName())
                        .dbUrl(tableInfo.getUrl())
                        .fields(this.columInfoToGoJsField(tableInfo.getColumns()))
                        .build());
                this.iteratorTree(tableInfo, goJsNodes, goJsLinks);
            });

            return GoJsGraphModel.builder()
                    .goJsLinks(goJsLinks)
                    .goJsNodes(goJsNodes.stream().filter(distinctByKey(GoJsNode::getNodeId))
                            .collect(Collectors.toList()))
                    .build();
        } else {
            return null;
        }

    }

    public  <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>(16);
        return object -> seen.putIfAbsent(keyExtractor.apply(object), Boolean.TRUE) == null;
    }


    public void iteratorTree(TableInfo tableInfo, List<GoJsNode> goJsNodes, List<GoJsLink> goJsLinks) {
        if (tableInfo != null) {
            if (tableInfo.getTables() != null && tableInfo.getTables().size() > 0) {
                for (TableInfo index : tableInfo.getTables()) {
                    goJsNodes.add(GoJsNode.builder()
                            .nodeId(index.getId())
                            .key(index.getTableName())
                            .srType("[" + SourceType.getNameByType(index.getSrType()) + "]")
                            .dbUrl(tableInfo.getUrl())
                            .fields(this.columInfoToGoJsField(index.getColumns()))
                            .build());
                    goJsLinks.add(GoJsLink.builder()
                            .from(tableInfo.getTableName())
                            .to(index.getTableName())
                            .color(Color.RED)
                            .build());
                    if (index.getTables() != null && index.getTables().size() > 0) {
                        this.iteratorTree(index, goJsNodes, goJsLinks);
                    }
                }
            }

        }
    }

    public List<GoJsNodeField> columInfoToGoJsField(List<ColumnInfo> columnInfos) {
        List<GoJsNodeField> goJsNodeFields = new ArrayList<>();
        if (columnInfos != null && columnInfos.size() > 0) {
            columnInfos.forEach(col->{
                goJsNodeFields.add(GoJsNodeField.builder()
                        .fieldId(col.getId())
                        .name(col.getColName())
                        .info(StringUtils.isEmpty(col.getComment()) ? "-" : col.getComment())
                        .build());
            });
        }
        return goJsNodeFields;
    }

    @Override
    public GridPage pageQuery(Pageable pageable) {
        return GridPage.of(tableInfoDao.pageQuery(pageable));
    }
}
