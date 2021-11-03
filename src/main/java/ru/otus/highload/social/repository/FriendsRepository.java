package ru.otus.highload.social.repository;

public interface FriendsRepository {

    boolean friendRecordExists(long first, long second);

    void createFriendRecord(long first, long second);

}
