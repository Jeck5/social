package ru.otus.highload.social.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {

    @Bean
    SimpleJdbcInsert simpleJdbcInsert(DataSource dataSource) {
        SimpleJdbcInsert insertUser = new SimpleJdbcInsert(dataSource);
        insertUser.setTableName("users");
        insertUser.setGeneratedKeyName("id");
        return insertUser;
    }
}
