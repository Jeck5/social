package ru.otus.highload.social.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.highload.social.dto.UserDto;
import ru.otus.highload.social.dto.UserWithFriendsDto;
import ru.otus.highload.social.model.Role;
import ru.otus.highload.social.model.SecurityUser;
import ru.otus.highload.social.model.User;
import ru.otus.highload.social.repository.FriendsRepository;
import ru.otus.highload.social.repository.UserRepository;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    public static final int ENCODE_STRENGTH = 12;
    private final UserRepository userRepository;
    private final FriendsRepository friendsRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return SecurityUser.fromUser(userRepository.getUserByLogin(s));
    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.getUserByLogin(login);
    }

    @Override
    public User saveUser(User user) {
        user.setRole(Role.USER);
        user.setPassword(new BCryptPasswordEncoder(ENCODE_STRENGTH).encode(user.getPassword()));
        return userRepository.saveUser(user);
    }

    @Override
    public void addToFriends(long friendId) {
        Long currentUserId = getCurrentUserId();
        if (currentUserId == friendId || friendsRepository.friendRecordExists(currentUserId, friendId)) {
            throw new UnsupportedOperationException(
                    "Same user or already friends: " + currentUserId + " and " + friendId);
        } else {
            friendsRepository.createFriendRecord(currentUserId, friendId);
        }
    }

    @Override
    public void deleteFromFriends(long friendId) {
        Long currentUserId = getCurrentUserId();
        if (currentUserId == friendId || !friendsRepository.friendRecordExists(currentUserId, friendId)) {
            throw new UnsupportedOperationException(
                    "Same user or already not friends: " + currentUserId + " and " + friendId);
        } else {
            friendsRepository.removeFriendRecord(currentUserId, friendId);
        }
    }

    @Override
    public void enrichWithFriends(UserWithFriendsDto userWithFriendsDto) {
        userWithFriendsDto.setFriends(userRepository.findAllFriends(userWithFriendsDto.getId()));
    }

    @Override
    public boolean isUserFriend(long id) {
        return friendsRepository.friendRecordExists(getCurrentUserId(), id);
    }

    @Override
    public boolean isUserNotFriend(long id) {
        return !friendsRepository.friendRecordExists(getCurrentUserId(), id) && !Objects.equals(id, getCurrentUserId());
    }

    @Override
    public String getCurrentUserLogin() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public List<UserDto> findUsersByNames(String firstName, String lastName) {
        return userRepository.findUsersByNames(firstName, lastName);
    }

    private Long getCurrentUserId() {
        return getUserByLogin(getCurrentUserLogin()).getId();
    }
}
