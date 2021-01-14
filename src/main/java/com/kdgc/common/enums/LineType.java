package com.kdgc.common.enums;

/**
 * @author jczhou
 * @description 线类型
 * @date 2020/7/20
 **/
public enum LineType {

    /**
     * 表与表线
     */
    LINE_TABLE_TO_TABLE("0"),

    /**
     * 表与列线
     */
    LINE_TABLE_TO_COLUM("1"),

    /**
     * 列与列线
     */
    LINE_COLUMN_TO_COLUMN("2");


    private String type;

    LineType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
