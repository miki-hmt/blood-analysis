package com.kdgc.model;

import com.kdgc.common.enums.LineType;

import java.io.Serializable;

/**
 * @author jczhou
 * @description 节点依赖关系
 * @date 2020/7/22
 **/
public class BaseRel implements Serializable {

    private String start;

    private String end;

    private LineType type;

}
