package com.kdgc.common.constant;

/**
 * @author jczhou
 * @description
 * @date 2020/7/22
 **/
public class SysCons {

    public final static String SUCCESS = "操作成功";

    public final static String ERROR = "操作失败";

    public final static String NULL_FORMAT = "参数【%s】不能为空";

    public static String nullParam(String paramName) {
        return String.format(SysCons.NULL_FORMAT, paramName);
    }

}
