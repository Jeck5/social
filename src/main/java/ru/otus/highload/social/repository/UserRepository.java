package ru.otus.highload.social.repository;

import ru.otus.highload.social.model.User;

public interface UserRepository {

    User getUserByLogin(String login);

    User saveUser(User user);
}
