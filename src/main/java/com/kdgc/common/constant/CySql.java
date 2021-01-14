package com.kdgc.common.constant;

import com.kdgc.entity.ColumnInfo;
import com.kdgc.entity.TableInfo;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author jczhou
 * @description
 * @date 2020/7/23
 **/
public class CySql {

    /**
     * 根据url,dbType,name 插入更新table_obj的sql模板
     */
    public static String MERGE_TABLE = "merge(n:table_obj{name:'%s', url:'%s', dbType:'%s'}) set n.srType='%s'";

    /**
     * 根据url,dbType,name 删除table_obj的节点sql模板
     */
    public static String DELETE_TABLE = "match(n:table_obj{name:'%s', url:'%s', dbType:'%s'}) delete n";

    /**
     * 根据url,dbType,name获取节点，并创建table_obj之间关系的sql模板
     */
    public static String MERGE_TABLE_TO_TABLE = "match (n:table_obj), (m:table_obj) \n" +
            "where n.name='%s' and n.url='%s' and n.dbType='%s'\n" +
            " and m.name='%s' and m.url='%s' and m.dbType='%s'\n" +
            "merge (n) -[r:"+ Rel.TABLE_TO_TABLE+"{start:'%s', end:'%s'}] -> (m)";

    /**
     * 根据url,tbname,name 插入更新column_obj的sql模板
     */
    public static String MERGE_COLUMN = "merge(n:column_obj{name:'%s', url:'%s', tbname:'%s'}) \n" +
            "set n.coltype='%s' set n.comment='%s'";

    /**
     * 根据url,tbname,name 删除column_obj的节点sql模板
     */
    public static String DELETE_COLUMN = "match(n:column_obj{name:'%s', url:'%s', tbname:'%s'}) delete n";

    /**
     * 根据url,tbname 删除column_obj的节点集合sql模板
     */
    public static String DELETE_COLUMNS = "match(n:column_obj{url:'%s', tbname:'%s'}) delete n";

    /**
     * 表--》列（指定列） 的关系sql模板
     */
    public static String MERGE_TABLE_TO_COLUMN = "match (n:table_obj),(m:column_obj) \n" +
            "where n.url='%s' and n.dbType='%s' and n.name='%s' \n" +
            "and m.url='%s' and m.tbname='%s' and m.name='%s'\n" +
            "merge (n) -[r:"+ Rel.TABLE_TO_COLUMN+"{start:'%s', end:'%s'}] -> (m) ";

    /**
     * 表--》列的关系sql模板
     */
    public static String MERGE_TABLE_TO_COLUMNS = "match (n:table_obj),(m:column_obj) \n" +
            "where n.url='%s' and n.dbType='%s' and n.name='%s' \n" +
            "and m.url='%s' and m.tbname='%s'\n" +
            "merge (n) -[r:"+ Rel.TABLE_TO_COLUMN+"{start:'%s', end:'%s.'+m.name}] -> (m) ";

    /**
     * 删除依赖关系
     */
    public static String DELETE_REL = "match()-[r:%s]-() where r.start='%s' or r.end='%s' return r";

    /**
     * 列--》列映射关系sql模板
     */
    public static String MERGE_COLUMN_TO_COLUMN = "match (n:column_obj),(m:column_obj) \n" +
            "where n.url='%s' and n.tbname='%s' and n.name='%s' and m.url='%s' and m.tbname='%s' and m.name='%s'\n" +
            " merge (n) -[r:"+ Rel.COLUMN_TO_COLUMN+"{start:'%s', end:'%s'}] -> (m) return r";


    public static String getMergeTableSql(TableInfo tableInfo) {
        return CySql.getMergeTableSql(tableInfo.getUrl(), tableInfo.getDbType(), tableInfo.getTableName(), tableInfo.getSrType());
    }

    public static String getMergeTableSql(String url, String dbType, String tableName, String srType) {
        Assert.notNull(url, SysCons.nullParam("url"));
        Assert.notNull(dbType, SysCons.nullParam("dbType"));
        Assert.notNull(tableName, SysCons.nullParam("tableName"));
        return String.format(CySql.MERGE_TABLE, tableName, url, dbType, srType);
    }

    public static String getDeleteTableSql(TableInfo tableInfo) {
        return CySql.getDeleteTableSql(tableInfo.getUrl(), tableInfo.getDbType(), tableInfo.getTableName());
    }

    public static String getDeleteTableSql(String url, String dbType, String tableName) {
        Assert.notNull(url, SysCons.nullParam("url"));
        Assert.notNull(dbType, SysCons.nullParam("dbType"));
        Assert.notNull(tableName, SysCons.nullParam("tableName"));
        return String.format(CySql.DELETE_TABLE, tableName, url, dbType);
    }

    public static String getMergeTableToTableSql(TableInfo sTable, TableInfo rTable) {
        return CySql.getMergeTableToTableSql(sTable.getUrl(), sTable.getDbType(), sTable.getTableName(), rTable.getUrl(), rTable.getDbType(), rTable.getTableName());
    }

    public static String getMergeTableToTableSql(String url, String dbType, String tableName, String url2, String dbType2, String tableName2) {
        Assert.notNull(url, SysCons.nullParam("url"));
        Assert.notNull(dbType, SysCons.nullParam("dbType"));
        Assert.notNull(tableName, SysCons.nullParam("tableName"));
        Assert.notNull(url, SysCons.nullParam("url2"));
        Assert.notNull(dbType, SysCons.nullParam("dbType2"));
        Assert.notNull(tableName, SysCons.nullParam("tableName2"));
        return String.format(CySql.MERGE_TABLE_TO_TABLE, tableName, url, dbType, tableName2, url2, dbType2, CySql.getLinkNodeStr(url, tableName), CySql.getLinkNodeStr(url2, tableName2));
    }


    public static String getMergeColumnSql(ColumnInfo columnInfo) {
        return CySql.getMergeColumnSql(columnInfo.getColName(), columnInfo.getColType(), columnInfo.getComment(), columnInfo.getUrl(), columnInfo.getTableName());
    }

    public static String getMergeColumnSql(String colName, String colType, String comment, String url, String tableName) {
        Assert.notNull(url, SysCons.nullParam("url"));
        Assert.notNull(colType, SysCons.nullParam("colType"));
        Assert.notNull(colName, SysCons.nullParam("colName"));
        Assert.notNull(tableName, SysCons.nullParam("tableName"));
        return String.format(CySql.MERGE_COLUMN, colName, url, tableName, colType, StringUtils.isEmpty(comment) ? "" : comment);
    }

    public static String getDeleteColumnSql(String colName, String url, String tableName) {
        return String.format(CySql.DELETE_COLUMN, colName, url, tableName);
    }

    public static String getDeleteColumnSql(ColumnInfo columnInfo) {
        return CySql.getDeleteColumnSql(columnInfo.getColName(), columnInfo.getUrl(), columnInfo.getTableName());
    }

    public static String getDeleteColumnsSql(String url, String tableName) {
        return String.format(CySql.DELETE_COLUMNS, url, tableName);
    }

    public static String getDeleteColumnsSql(TableInfo tableInfo) {
        return CySql.getDeleteColumnsSql(tableInfo.getUrl(), tableInfo.getTableName());
    }

    public static String getMergeTableToColumnSql(String url, String dbType, String tableName, String colName) {
        Assert.notNull(url, SysCons.nullParam("url"));
        Assert.notNull(dbType, SysCons.nullParam("dbType"));
        Assert.notNull(tableName, SysCons.nullParam("tableName"));
        Assert.notNull(colName, SysCons.nullParam("colName"));
        return String.format(CySql.MERGE_TABLE_TO_COLUMN, url, dbType, tableName, url, tableName, colName, CySql.getLinkNodeStr(url, tableName), CySql.getLinkNodeStr(url, tableName, colName));
    }

    public static String getMergeTableToColumnSql(TableInfo tableInfo, String colName) {
        return CySql.getMergeTableToColumnSql(tableInfo.getUrl(), tableInfo.getDbType(), tableInfo.getTableName(), colName);
    }

    public static String getMergeTableToColumnsSql(String url, String dbType, String tableName) {
        Assert.notNull(url, SysCons.nullParam("url"));
        Assert.notNull(dbType, SysCons.nullParam("dbType"));
        Assert.notNull(tableName, SysCons.nullParam("tableName"));
        return String.format(CySql.MERGE_TABLE_TO_COLUMNS, url, dbType, tableName, url, tableName, CySql.getLinkNodeStr(url, tableName), CySql.getLinkNodeStr(url, tableName));
    }

    public static String getMergeTableToColumnsSql(TableInfo tableInfo, String colName) {
        return CySql.getMergeTableToColumnsSql(tableInfo.getUrl(), tableInfo.getDbType(), tableInfo.getTableName());
    }

    public static String getDeleteRelSql(String relType, String start, String end) {
        Assert.notNull(relType, SysCons.nullParam("relType"));
        Assert.notNull(start, SysCons.nullParam("start"));
        Assert.notNull(end, SysCons.nullParam("end"));
        if (Rel.getRels().indexOf(relType) == -1) {
            Assert.state(false, "relType无效");
        }
        return String.format(CySql.DELETE_REL, relType, start, end);
    }

    public static String getDeleteRelSql(String relType, String startOrEnd) {
        return CySql.getDeleteRelSql(relType, startOrEnd, startOrEnd);
    }

    public static String getMergeTableToColumn(ColumnInfo scol, ColumnInfo rcol) {
        return CySql.getMergeColumnToColumn(scol.getUrl(), scol.getTableName(), scol.getColName(),
                rcol.getUrl(), rcol.getTableName(), rcol.getColName());
    }

    public static String getMergeColumnToColumn(String sUrl, String sTableName, String sColName, String rUrl, String rTableName, String rColName) {
        Assert.notNull(sUrl, SysCons.nullParam("sUrl"));
        Assert.notNull(sTableName, SysCons.nullParam("sTableName"));
        Assert.notNull(sColName, SysCons.nullParam("sColName"));
        Assert.notNull(rUrl, SysCons.nullParam("rUrl"));
        Assert.notNull(rTableName, SysCons.nullParam("rTableName"));
        Assert.notNull(rColName, SysCons.nullParam("rColName"));
        return String.format(CySql.MERGE_COLUMN_TO_COLUMN, sUrl, sTableName, sColName,
                rUrl, rTableName, rColName,
                CySql.getLinkNodeStr(sUrl, sTableName, sColName),
                CySql.getLinkNodeStr(rUrl, rTableName, rColName));
    }


    public static String getLinkNodeStr(String... args) {
        String formatStr = "";
        for(String arg : args) {
            formatStr += "%s.";
        }
        return formatStr.length() == 0 ? "" : String.format(formatStr.substring(0, formatStr.length() - 1), args);
    }

}
