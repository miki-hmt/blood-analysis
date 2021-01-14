package com.kdgc.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author jczhou
 * @description
 * @date 2020/7/22
 **/
@Setter
@Getter
@Builder
public class ColumnModel {

    private String columnName;

    private String type;

    private String comment;

}
