package ru.otus.highload.social.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.otus.highload.social.dto.FriendDto;
import ru.otus.highload.social.dto.UserDto;
import ru.otus.highload.social.model.User;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> USER_ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private static final BeanPropertyRowMapper<FriendDto> FRIEND_ROW_MAPPER = BeanPropertyRowMapper.newInstance(FriendDto.class);

    private static final BeanPropertyRowMapper<UserDto> USER_SEARCH_ROW_MAPPER = BeanPropertyRowMapper.newInstance(UserDto.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Override
    public User getUserByLogin(String login) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM users WHERE login=?", USER_ROW_MAPPER, login);
        } catch (Exception e) {
            log.warn("User not found by login {}", login);
            return null;
        }
    }

    @Override
    public User saveUser(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        Number newKey = insertUser.executeAndReturnKey(parameterSource);
        user.setId(newKey.longValue());
        return user;
    }

    @Override
    public List<FriendDto> findAllFriends(Long id) {
        return jdbcTemplate.query("SELECT id, login FROM users WHERE id IN( " +
                "SELECT id1 FROM friends where id2=? UNION ALL " +
                "SELECT id2 FROM friends where id1=?)", FRIEND_ROW_MAPPER, id, id);
    }

    @Override
    public List<UserDto> findUsersByNames(String firstName, String lastName) {
        return jdbcTemplate.query("SELECT id, login, first_name, last_name FROM users WHERE " +
                "first_name like ? and last_name like ? ", USER_SEARCH_ROW_MAPPER, firstName + "%", lastName + "%");
    }
}
