package ru.otus.highload.social.service;

import ru.otus.highload.social.model.User;

import java.util.List;

public interface UserService {

    User getUserByLogin(String login);

    User saveUser(User user);

    List<User> getAllUsers();

    void addToFriends(long friendId);
}
