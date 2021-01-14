package com.kdgc.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author jczhou
 * @description
 * @date 2020/7/24
 **/
@Setter
@Getter
@Builder
public class GoJsNode implements Serializable {

    private Long nodeId;

    private String dbUrl;

    private String key;

    private String srType;

    private List<GoJsNodeField> fields;

    /**
     * 位置 如: 321 -98
     */
    private String loc;
}
