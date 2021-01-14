package com.kdgc.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author jczhou
 * @description
 * @date 2020/7/24
 **/
@Setter
@Getter
@Builder
public class GoJsNodeField implements Serializable {

    private Long fieldId;

    private String name;

    private String info;

    private String color;

    private String figure;
}
