package com.kdgc.common.constant;

/**
 * @author jczhou
 * @description
 * @date 2020/7/22
 **/
public class Rel {

    public final static String TABLE_TO_TABLE = "来源";

    public final static String TABLE_TO_COLUMN = "列";

    public final static String COLUMN_TO_COLUMN = "映射";

    /**
     * 最大关系深度
     */
    public final static int MAX_REL_DEEP = 10;

    public static String getRels() {
        return String.format("%s.%s.%s", Rel.TABLE_TO_TABLE, Rel.TABLE_TO_COLUMN, Rel.COLUMN_TO_COLUMN);
    }
}
