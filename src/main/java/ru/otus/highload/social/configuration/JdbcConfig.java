package ru.otus.highload.social.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {

    @Bean("insertUser")
    SimpleJdbcInsert simpleJdbcInsertUser(DataSource dataSource) {
        SimpleJdbcInsert insertUser = new SimpleJdbcInsert(dataSource);
        insertUser.setTableName("users");
        insertUser.setGeneratedKeyName("id");
        return insertUser;
    }

    @Bean("insertDialog")
    SimpleJdbcInsert simpleJdbcInsertDialog(DataSource dataSource) {
        SimpleJdbcInsert insertUser = new SimpleJdbcInsert(dataSource);
        insertUser.setTableName("dialogs");
        insertUser.setGeneratedKeyName("id");
        return insertUser;
    }

}
