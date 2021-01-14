package com.kdgc.model;

import com.kdgc.entity.TableInfo;
import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.io.Serializable;

/**
 * @author jczhou
 * @description
 * @date 2020/7/21
 **/
@QueryResult
@Data
public class TableLink implements Serializable {

    private TableInfo stable;

    private TableInfo rtable;

}
