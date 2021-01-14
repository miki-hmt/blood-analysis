package com.kdgc.common.config;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jczhou
 * @description
 * @date 2020/7/23
 **/
@Configuration
public class Neo4jConfig {

    @Value("${blotUri}")
    private String uri;

    @Value("${spring.data.neo4j.username}")
    private String username;

    @Value("${spring.data.neo4j.password}")
    private String password;

    /**
     * 图数据库驱动模式
     *
     * @return
     */
    @Bean
    public Driver neo4jDriver() {
        return GraphDatabase.driver(uri, AuthTokens.basic(username,password));
    }

    @Bean
    public Session neo4jSession() {
        return this.neo4jDriver().session();
    }
}
