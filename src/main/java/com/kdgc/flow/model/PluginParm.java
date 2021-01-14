package com.kdgc.flow.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jczhou
 * @description 流程节点插件信息
 * @date 2020/7/31
 **/
@Data
public class PluginParm implements Serializable {

    /**
     * 数据库读取参数
     */
    private String sourceSql;

    private String paramColumn;

    private String extractType;

    private String paramTable;


    /**
     * 数据库写入参数
     */
    private boolean isSingleExecute;

    private String updateKeys;

    private String deleteClause;

    private String columnList;

    private boolean isUpdate;

    private String tableName;

    private String columnMapping;

    /**
     * 文件参数
     */
    private String folderPath;

    private String deleteSourceFile;

    private String backupPath;

    private String includeWildCard;

    private String excludeWildCard;

}
