package com.kdgc.common.enums;

/**
 * @author jczhou
 * @description 交换流程插件类型
 * @date 2020/8/3
 **/
public enum PluginType {

    /**
     * 数据库抽取类型
     */
    DB_READER("DatabaseReaderPlugin"),

    /**
     * 数据库写入类型
     */
    DB_WRITER("DatabaseWriterPlugin"),

    /**
     * JSON文件读取类型
     */
    JSON_READER("JSONReaderPlugin"),

    /**
     * CSV文件读取类型
     */
    CSV_READER("CSVReaderPlugin"),

    /**
     * EXCEL文件读取类型
     */
    EXCEL_READER("ExcelReaderPlugin");



    private String pluginName;

    PluginType(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getPluginName() {
        return pluginName;
    }
}
