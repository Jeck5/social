package ru.otus.highload.social.repository;

import ru.otus.highload.social.dto.FriendDto;
import ru.otus.highload.social.model.User;

import java.util.List;

public interface UserRepository {

    User getUserByLogin(String login);

    User saveUser(User user);

    List<User> getAllUsers();

    List<FriendDto> findAllFriends(Long id);
}
