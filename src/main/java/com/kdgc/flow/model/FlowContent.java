package com.kdgc.flow.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author jczhou
 * @description
 * @date 2020/7/31
 **/
@Data
public class FlowContent implements Serializable {

    private String title;

    private List<Map<String, FlowNode>> nodes;

    private List<Map<String, FlowLine>> lines;

    private Object areas;

    private Integer initNum;
 }
