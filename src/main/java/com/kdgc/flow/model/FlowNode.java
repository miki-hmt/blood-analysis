package com.kdgc.flow.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author jczhou
 * @description
 * @date 2020/7/31
 **/
@Data
public class FlowNode implements Serializable {

    private String pluginType;

    private Integer top;

    private Integer left;

    private String name;

    private String peercode;

    private Integer width;

    private boolean alt;

    private String dataName;

    private String id;

    private String type;

    private Integer height;
}
