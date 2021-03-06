package ru.otus.highload.social.repository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.otus.highload.social.model.Role;
import ru.otus.highload.social.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;

//@Component
public class InMemoryUserRepositoryImpl {//} implements UserRepository {

    private static final List<User> USER_LIST = new ArrayList<>(Collections.singletonList(
            User.builder().login("user1").password(new BCryptPasswordEncoder(12).encode("user1"))
                    .id(32L).role(Role.USER).build()
    ));

    //@Override
    public User getUserByLogin(String login) {
        return USER_LIST.stream()
                .filter(user -> user.getLogin().equalsIgnoreCase(login))
                .findFirst()
                .orElseThrow();
    }

    //@Override
    public User saveUser(User user) {
        USER_LIST.removeIf(u -> u.getLogin().equalsIgnoreCase(user.getLogin()));
        user.setId(32L);
        user.setRole(Role.USER);
        user.setPassword(new BCryptPasswordEncoder(12).encode(user.getPassword()));
        USER_LIST.add(user);
        return user;
    }

    //@Override
    public List<User> getAllUsers() {
        return USER_LIST;
    }


}
