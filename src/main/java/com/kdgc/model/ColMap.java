package com.kdgc.model;

import com.kdgc.entity.ColumnInfo;
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
public class ColMap implements Serializable {

    private ColumnInfo scol;

    private ColumnInfo rcol;
}

