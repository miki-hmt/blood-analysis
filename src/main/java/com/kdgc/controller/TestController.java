package com.kdgc.controller;

import com.kdgc.common.enums.SourceType;
import com.kdgc.entity.ColumnInfo;
import com.kdgc.entity.TableInfo;
import com.kdgc.service.ColumnInfoService;
import com.kdgc.service.TableInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author jczhou
 * @description
 * @date 2020/7/23
 **/
@Controller
@RequestMapping(value = "/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TableInfoService tableInfoService;
    @Autowired
    private ColumnInfoService columnInfoService;

    @RequestMapping(value = "/console")
    @ResponseBody
    public void console() {
        logger.info("console -- info");
        logger.error("console -- error");
        logger.warn("console -- warn");
    }

    @RequestMapping(value = "/saveTable")
    @ResponseBody
    public void saveTable(String tableName) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            TableInfo tableInfo = new TableInfo();
            tableInfo.setUrl("127.0.0.1:3306/crm");
            tableInfo.setDbType("mysql");
            tableInfo.setTableName(tableName + "_" + i);
            tableInfo.setSrType(SourceType.DB_TABLE.getType());
            tableInfoService.save(tableInfo);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @RequestMapping(value = "/delTable")
    @ResponseBody
    public void delTable(String tableName) {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setUrl("192.168.62.21:5258/test");
        tableInfo.setDbType("gbase");
        tableInfo.setTableName(tableName);
        tableInfoService.del(tableInfo);
    }

    @RequestMapping(value = "/saveCol")
    @ResponseBody
    public void saveCol(String tableName, String colName) {
        ColumnInfo columnInfo = new ColumnInfo();
        columnInfo.setUrl("192.168.62.21:5258/test");
        columnInfo.setColName(colName);
        columnInfo.setTableName(tableName);
        columnInfo.setColType("varchar");
        columnInfoService.save(columnInfo);
    }

    @RequestMapping(value = "/delCol")
    @ResponseBody
    public void delCol(String tableName, String colName) {
        ColumnInfo columnInfo = new ColumnInfo();
        columnInfo.setUrl("192.168.62.21:5258/test");
        columnInfo.setColName(colName);
        columnInfo.setTableName(tableName);
        columnInfo.setColType("varchar");
        columnInfoService.del(columnInfo);
    }

    @RequestMapping(value = "/saveTableToColumn")
    @ResponseBody
    public void saveTableToColumn(String tableName, String colName) {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setUrl("192.168.62.21:5258/test");
        tableInfo.setDbType("gbase");
        tableInfo.setTableName(tableName);

        ColumnInfo columnInfo = new ColumnInfo();
        columnInfo.setUrl("192.168.62.21:5258/test");
        columnInfo.setColName(colName);
        columnInfo.setTableName(tableName);
        columnInfo.setColType("varchar");

        tableInfoService.saveTableToColumnRel(tableInfo, columnInfo);
    }

    @RequestMapping(value = "/saveTableRel")
    @ResponseBody
    public void saveTableRel(String sTableName, String rTableName) {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setUrl("192.168.62.21:5258/test");
        tableInfo.setDbType("gbase");
        tableInfo.setTableName(sTableName);

        TableInfo tableInfo1 = new TableInfo();
        tableInfo1.setUrl("192.168.62.21:5258/test");
        tableInfo1.setDbType("gbase");
        tableInfo1.setTableName(rTableName);

        tableInfoService.saveTableRel(tableInfo, tableInfo1);
    }

    @RequestMapping(value = "/saveColumnToColumn")
    @ResponseBody
    public void saveColumnToColumn() {
        ColumnInfo columnInfo = new ColumnInfo();
        columnInfo.setUrl("192.168.62.20/kdgcdata/jsonfile");
        columnInfo.setColName("id");
        columnInfo.setTableName("test.json");

        ColumnInfo columnInfo2 = new ColumnInfo();
        columnInfo2.setUrl("192.168.62.20:3306/dcweb");
        columnInfo2.setColName("ts_id");
        columnInfo2.setTableName("test");
        tableInfoService.saveColumnToColumnRel(columnInfo, columnInfo2);
    }

}
