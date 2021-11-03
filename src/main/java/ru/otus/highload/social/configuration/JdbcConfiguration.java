package ru.otus.highload.social.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

@Configuration
public class JdbcConfiguration {

/*
    @Bean
    public SimpleJdbcInsertOperations todoSimpleJdbcInsertOperations(DataSource dataSource) {
        return new SimpleJdbcInsert(dataSource)
                .withTableName("todo")
                .usingColumns("id", "task", "done")
                .usingGeneratedKeyColumns("date_created");
    }
*/

    @Bean
    SimpleJdbcInsert simpleJdbcInsert(DataSource dataSource) {
        return new SimpleJdbcInsert(dataSource);
    }
}
