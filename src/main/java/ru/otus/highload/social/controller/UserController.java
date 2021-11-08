package ru.otus.highload.social.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.otus.highload.social.dto.UserDto;
import ru.otus.highload.social.dto.UserWithFriendsDto;
import ru.otus.highload.social.model.User;
import ru.otus.highload.social.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class UserController {

    public static final String FIND_BY_LOGIN = "find-by-login";
    private final UserService userService;
    private final ApplicationContext applicationContext;

    @GetMapping("/users/{login}")
        //TODO secure?
    String getByLogin(@PathVariable("login") String login, Model model) {
        User user = userService.getUserByLogin(login);
        UserWithFriendsDto userWithFriendsDto = dtoFromUser(user);
        userService.enrichWithFriends(userWithFriendsDto);
        model.addAttribute("userDto", userWithFriendsDto);
        return "users";
    }

    @SneakyThrows
    @GetMapping("/current-user")
    @PreAuthorize("hasAuthority('users:read')")
    void getCurrentLogin(HttpServletResponse response) {
        redirectToCurrentUserPage(response);
    }

    @SneakyThrows
    @GetMapping("/not-found")
    @PreAuthorize("hasAuthority('users:read')")
    String getNotFound() {
        return "not-found";
    }

    @SneakyThrows
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('users:read')")
    void find(@RequestParam("action") @NotEmpty String action, @RequestParam("value") @NotEmpty String value,
              HttpServletResponse response) {

        if (action.equalsIgnoreCase(FIND_BY_LOGIN)) {
            User user = userService.getUserByLogin(value);
            if (user == null) {
                response.sendRedirect("/not-found");
            } else {
                response.sendRedirect("/users/" + value);
            }
        } else {
            throw new UnsupportedOperationException(action + " action is not supported");
        }
    }

    @SneakyThrows
    @PreAuthorize("hasAuthority('users:read')")
    @PostMapping("/add-to-friends/{friend-id}")
    void addUserToFriends(@PathVariable("friend-id") long friendId, HttpServletResponse response) {
        userService.addToFriends(friendId);
        redirectToCurrentUserPage(response);
    }

    @SneakyThrows
    @PreAuthorize("hasAuthority('users:read')")
    @PostMapping("/delete-from-friends/{friend-id}")
    void deleteUserFromFriends(@PathVariable("friend-id") long friendId, HttpServletResponse response) {
        userService.deleteFromFriends(friendId);
        redirectToCurrentUserPage(response);
    }

    @GetMapping("/auth/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/auth/register")
    public String getRegistrationPage(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "register";
    }

    @PostMapping("/auth/register")
    @SneakyThrows
    public String registerNewUser(@ModelAttribute @Valid UserDto userDto,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                  HttpServletRequest request, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userDto", userDto);
            return "register";
        }
        model.addAttribute("userDto", userDto);
        User user = userFromDto(userDto);
        userService.saveUser(user);
        request.login(userDto.getLogin(), userDto.getPassword());
        redirectAttributes.addAttribute("login", user.getLogin());
        return "redirect:/users/{login}"; //TODO forward vs redirect
    }

    private void redirectToCurrentUserPage(HttpServletResponse response) throws IOException {
        response.sendRedirect("/users/" + userService.getCurrentUserLogin());
    }

    private User userFromDto(UserDto userDto) {
        return User.builder()
                .age(userDto.getAge())
                .city(userDto.getCity())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .login(userDto.getLogin())
                .interests(userDto.getInterests())
                .gender(userDto.getGender())
                .password(userDto.getPassword())//TODO when encode ?
                .build();
    }

    private UserWithFriendsDto dtoFromUser(User user) {
        return UserWithFriendsDto.builder()
                .id(user.getId())
                .age(user.getAge())
                .city(user.getCity())
                .gender(user.getGender())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .interests(user.getInterests())
                .login(user.getLogin())
                .build();
    }

}
