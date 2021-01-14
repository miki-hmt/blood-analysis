package com.kdgc.common.model;

import com.kdgc.common.constant.SysCons;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jczhou
 * @description
 * @date 2020/7/22
 **/
@Data
public class JsonResult implements Serializable {

    private boolean success;

    private String msg;

    private Object data;

    public JsonResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public JsonResult(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    public JsonResult(boolean success, String msg, Object data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public static JsonResult success() {
        return new JsonResult(true, SysCons.SUCCESS);
    }

    public static JsonResult error() {
        return new JsonResult(false, SysCons.ERROR);
    }

    public static JsonResult success(String msg) {
        return new JsonResult(true, msg);
    }

    public static JsonResult error(String msg) {
        return new JsonResult(false, msg);
    }

    public static JsonResult success(Object data) {
        return new JsonResult(true, SysCons.SUCCESS, data);
    }

    public static JsonResult error(Object data) {
        return new JsonResult(false, SysCons.ERROR, data);
    }

    public static JsonResult success(String msg, Object data) {
        return new JsonResult(true, msg, data);
    }

    public static JsonResult error(String msg, Object data) {
        return new JsonResult(false, msg, data);
    }

}
