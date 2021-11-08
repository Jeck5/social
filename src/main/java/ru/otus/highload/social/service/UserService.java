package ru.otus.highload.social.service;

import ru.otus.highload.social.dto.UserWithFriendsDto;
import ru.otus.highload.social.model.User;

import java.util.List;

public interface UserService {

    User getUserByLogin(String login);

    User saveUser(User user);

    List<User> getAllUsers();

    void addToFriends(long friendId);

    void deleteFromFriends(long friendId);

    void enrichWithFriends(UserWithFriendsDto userWithFriendsDto);

    boolean isUserFriend(long id);

    boolean isUserNotFriend(long id);
    String getCurrentUserLogin();
}
