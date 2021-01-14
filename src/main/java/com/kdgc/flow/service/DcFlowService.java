package com.kdgc.flow.service;

import com.alibaba.fastjson.JSON;
import com.kdgc.common.enums.PluginType;
import com.kdgc.common.enums.SourceType;
import com.kdgc.entity.ColumnInfo;
import com.kdgc.entity.TableInfo;
import com.kdgc.flow.dao.DcFlowDao;
import com.kdgc.flow.dao.DcTaskDao;
import com.kdgc.flow.entity.DcwebFlows;
import com.kdgc.flow.entity.DcwebTask;
import com.kdgc.flow.model.FlowContent;
import com.kdgc.flow.model.FlowLine;
import com.kdgc.flow.model.PluginParm;
import com.kdgc.service.ColumnInfoService;
import com.kdgc.service.TableInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author jczhou
 * @description
 * @date 2020/7/30
 **/
@Service
public class DcFlowService {

    private final static Logger logger = LoggerFactory.getLogger(DcFlowService.class);

    @Autowired
    private DcFlowDao dcFLowDao;
    @Autowired
    private DcTaskDao dcTaskDao;
    @Autowired
    private TableInfoService tableInfoService;
    @Autowired
    private ColumnInfoService columnInfoService;

    private final static Set<String> PLUGINID = new HashSet<String>() {{
        //数据库抽取插件
        add(PluginType.DB_READER.getPluginName());
        //数据库写入插件
        add(PluginType.DB_WRITER.getPluginName());
        //Excel文件读取插件
        add(PluginType.EXCEL_READER.getPluginName());
        //JSON文件读取插件
        add(PluginType.JSON_READER.getPluginName());
        //CSV文件读取插件
        add(PluginType.CSV_READER.getPluginName());
    }};

    public DcwebFlows findByFlowCode(String flowCode) {
        return dcFLowDao.findByFlowCode(flowCode);
    }


    @CacheEvict(value = "GoJsGraphModel", allEntries = true)
    public void flowToRel(String flowCode) {
        DcwebFlows dcwebFlows = dcFLowDao.findByFlowCode(flowCode);
        if (dcwebFlows != null) {
            FlowContent flowContent = JSON.parseObject(dcwebFlows.getFlowContent(), FlowContent.class);
            List<Map<String, FlowLine>> lines = flowContent.getLines();

            List<DcwebTask> tasks = dcTaskDao.findByFlowCode(flowCode);
            //将所有任务存入taskMap中，便于快速查找
            Map<String, Integer> taskMap = new HashMap<String, Integer>(16);
            if (tasks != null && tasks.size() > 0) {
                for (int i = 0; i < tasks.size(); i++) {
                    taskMap.put(tasks.get(i).getTskId().toString(), i);
                }
            }

            List<FlowLine> flowLines = new ArrayList<>();
            if (lines != null && lines.size() > 0) {
                lines.forEach(line->{
                    for (Map.Entry<String, FlowLine> entry : line.entrySet()) {
                        flowLines.add(entry.getValue());
                    }
                });
            }

            if (flowLines != null && flowLines.size() > 0) {
                flowLines.forEach(flowLine -> {
                    String from = flowLine.getFrom();
                    String to = flowLine.getTo();
                    if (PLUGINID.contains(tasks.get(taskMap.get(from)).getPluginId())
                            && PLUGINID.contains(tasks.get(taskMap.get(to)).getPluginId())) {
                        this.saveTasks(tasks.get(taskMap.get(from)), tasks.get(taskMap.get(to)));
                    }
                });
            }
        }
    }

    private void saveTasks(DcwebTask sTask, DcwebTask rTask) {
        PluginParm pluginParm = JSON.parseObject(rTask.getPluginParm(), PluginParm.class);
        if (pluginParm != null && !StringUtils.isEmpty(pluginParm.getColumnMapping())) {
            Map<String, String> colMap = JSON.parseObject(pluginParm.getColumnMapping(), Map.class);

            List<String> sCols = new ArrayList<>();
            List<String> rCols = new ArrayList<>();
            for (Map.Entry<String, String> entry : colMap.entrySet()) {
                sCols.add(entry.getKey());
                rCols.add(entry.getValue());
            }

            sTask.setCols(sCols);
            rTask.setCols(rCols);
        }

        TableInfo sTableInfo = this.taskToTableInfo(sTask);
        TableInfo rTableInfo = this.taskToTableInfo(rTask);
        this.saveTableInfo(sTableInfo, rTableInfo);
    }

    public TableInfo taskToTableInfo(DcwebTask task) {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setUrl(this.getNodeUrl(task));
        //TODO 类型无法获取
        tableInfo.setDbType("mysql");
        tableInfo.setSrType(this.pluginToSrType(task.getPluginId()));
        tableInfo.setTableName(this.getNodeName(task));

        if (task.getCols() != null && task.getCols().size() > 0) {
            List<ColumnInfo> columnInfos = new ArrayList<>();
            task.getCols().forEach(colName->{
                ColumnInfo columnInfo = new ColumnInfo();
                //TODO 字段无法获取
                columnInfo.setColType("String");
                columnInfo.setColName(colName);
                columnInfo.setTableName(tableInfo.getTableName());
                columnInfo.setComment("");
                columnInfo.setUrl(tableInfo.getUrl());
                columnInfos.add(columnInfo);
            });
            tableInfo.setColumns(columnInfos);
        }

        return tableInfo;
    }

    private String pluginToSrType(String plugin) {
        if (PluginType.DB_READER.getPluginName().equals(plugin) || PluginType.DB_WRITER.getPluginName().equals(plugin)) {
            return SourceType.DB_TABLE.getType();
        } else if (PluginType.EXCEL_READER.getPluginName().equals(plugin)){
            return SourceType.EXCEL.getType();
        } else if (PluginType.JSON_READER.getPluginName().equals(plugin)){
            return SourceType.JSON.getType();
        } else if (PluginType.CSV_READER.getPluginName().equals(plugin)){
            return SourceType.CSV.getType();
        } else {
            return "";
        }
    }

    private String getNodeUrl(DcwebTask task) {
        PluginParm pluginParm = JSON.parseObject(task.getPluginParm(), PluginParm.class);
        String prefix = task.getPeerCode() + ":" + pluginParm.getFolderPath() + "/";
        String plugin = task.getPluginId();
        if (PluginType.DB_READER.getPluginName().equals(plugin) || PluginType.DB_WRITER.getPluginName().equals(plugin)) {
            return task.getRsrcCode();
        } else if (PluginType.EXCEL_READER.getPluginName().equals(plugin)){
            return prefix + SourceType.EXCEL.getTypeName();
        } else if (PluginType.JSON_READER.getPluginName().equals(plugin)){
            return prefix + SourceType.JSON.getTypeName();
        } else if (PluginType.CSV_READER.getPluginName().equals(plugin)){
            return prefix + SourceType.CSV.getTypeName();
        } else {
            return "";
        }

    }

    private String getNodeName(DcwebTask task) {
        String plugin = task.getPluginId();
        PluginParm pluginParm = JSON.parseObject(task.getPluginParm(), PluginParm.class);
        String tableName = pluginParm.getTableName();
        if (PluginType.DB_READER.getPluginName().equals(plugin)) {
            String tableSql = pluginParm.getSourceSql().toLowerCase();
            return tableSql.split("from")[1].trim();
        } else if (PluginType.DB_WRITER.getPluginName().equals(plugin)){
            String[] table = pluginParm.getTableName().split("\\.");
            return table[table.length - 1];
        }  else {
            return StringUtils.isEmpty(task.getDataName())? task.getTskName() : task.getDataName();
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void saveTableInfo(TableInfo sTable, TableInfo rTable) {
        long startTime = System.currentTimeMillis();
        //表信息存入库中，并才创建表与表关系
        tableInfoService.save(sTable);
        tableInfoService.save(rTable);
        tableInfoService.saveTableRel(sTable, rTable);

        if (sTable.getColumns() != null) {
            //列信息存入库中
            for (int i = 0; i < sTable.getColumns().size(); i++) {
                columnInfoService.save(sTable.getColumns().get(i));
                columnInfoService.save(rTable.getColumns().get(i));
            }

            //创建表与列关系，列与列关系
            for (int i = 0; i < sTable.getColumns().size(); i++) {
                tableInfoService.saveTableToColumnRel(sTable, sTable.getColumns().get(i));
                tableInfoService.saveTableToColumnRel(rTable, rTable.getColumns().get(i));
                tableInfoService.saveColumnToColumnRel(sTable.getColumns().get(i), rTable.getColumns().get(i));
            }
        }

        long endTime = System.currentTimeMillis();
        logger.info(String.format("流程信息存入neo4j耗时：%s毫秒", endTime - startTime));
    }
}
