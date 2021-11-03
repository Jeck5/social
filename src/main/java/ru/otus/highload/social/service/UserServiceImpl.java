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
import ru.otus.highload.social.model.Role;
import ru.otus.highload.social.model.SecurityUser;
import ru.otus.highload.social.model.User;
import ru.otus.highload.social.repository.FriendsRepository;
import ru.otus.highload.social.repository.UserRepository;

import java.util.List;

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
    public List<User> getAllUsers() { //TODO delete?
        return userRepository.getAllUsers();
    }

    @Override
    public void addToFriends(long friendId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User currentUser = getUserByLogin(currentUserName);
        if (currentUser.getId() == friendId || friendsRepository.friendRecordExists(currentUser.getId(), friendId)) {
            throw new UnsupportedOperationException(
                    "Same user or already friends: " + currentUser.getId() + " and " + friendId);
        } else {
            friendsRepository.createFriendRecord(currentUser.getId(), friendId);
        }
    }
}
