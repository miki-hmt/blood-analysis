package com.kdgc.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author jczhou
 * @description
 * @date 2020/7/24
 **/
@Setter
@Getter
@Builder
public class GoJsLink {

    private String from;

    private String to;

    private String color;

}
