package ru.otus.highload.social.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcFriendsRepositoryImpl implements FriendsRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean friendRecordExists(long first, long second) {
        Long min = getMin(first, second);
        Long max = getMax(first, second);
        return jdbcTemplate.queryForObject(
                "select count(*) > 0 from friends where id1=?1 and id2=?2",
                Boolean.class, min, max);
    }

    @Override
    public void createFriendRecord(long first, long second) {
        Long min = getMin(first, second);
        Long max = getMax(first, second);
        jdbcTemplate.update("insert into friends (id1,id2) values (?1, ?2)", min, max);
    }

    private long getMin(long first, long second) {
        return Math.min(first, second);
    }

    private long getMax(long first, long second) {
        return Math.max(first, second);
    }

}
