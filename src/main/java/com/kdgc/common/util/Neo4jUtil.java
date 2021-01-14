package com.kdgc.common.util;

import com.alibaba.fastjson.JSON;
import com.kdgc.common.exception.CsqlExeException;
import org.neo4j.driver.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jczhou
 * @description neo4j工具类
 * @date 2020/7/20
 **/
@Component
public class Neo4jUtil {

    private final static Logger logger = LoggerFactory.getLogger(Neo4jUtil.class);

    @Autowired
    private Session session;

    public void executeSql(String sql) {
        logger.info("\n" +
                "-----------------------------------------------------------------------------\n"+
                "sql = { " +sql+" }\n"+
                "-----------------------------------------------------------------------------\n");
        session.writeTransaction( new TransactionWork<Integer>()
        {
            @Override
            public Integer execute( Transaction tx )
            {
                tx.run(sql);
                return 1;
            }
        } );

    }
}
