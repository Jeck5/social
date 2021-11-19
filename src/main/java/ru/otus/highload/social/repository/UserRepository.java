package ru.otus.highload.social.repository;

import ru.otus.highload.social.dto.FriendDto;
import ru.otus.highload.social.dto.UserDto;
import ru.otus.highload.social.model.User;

import java.util.List;

public interface UserRepository {

    User getUserByLogin(String login);

    User saveUser(User user);

    List<FriendDto> findAllFriends(Long id);

    List<UserDto> findUsersByNames(String firstName, String lastName);
}
