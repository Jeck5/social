package ru.otus.highload.social.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.otus.highload.social.model.User;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcUserRepositoryImpl implements UserRepository{

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Override
    public User getUserByLogin(String login) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE login=?", ROW_MAPPER, login);
    }

    @Override
    public User saveUser(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        insertUser.setTableName("users"); //TODO here?
        insertUser.setGeneratedKeyName("id");
        Number newKey = insertUser.executeAndReturnKey(parameterSource);
        user.setId(newKey.longValue());
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }
}
