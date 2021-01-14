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
public class GoJsGraphModel implements Serializable {

    private List<GoJsNode> goJsNodes;

    private List<GoJsLink> goJsLinks;
}
