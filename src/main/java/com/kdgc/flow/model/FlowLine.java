package com.kdgc.flow.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jczhou
 * @description
 * @date 2020/7/31
 **/
@Data
public class FlowLine implements Serializable {


    private String type;

    private String from;

    private String to;

    private String name;

}
