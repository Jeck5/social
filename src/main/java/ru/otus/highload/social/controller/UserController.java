package ru.otus.highload.social.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.otus.highload.social.dto.UserDto;
import ru.otus.highload.social.model.User;
import ru.otus.highload.social.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{login}")
    String getByLogin(@PathVariable("login") String login, Model model) {
        User user = userService.getUserByLogin(login);
        UserDto userDto = dtoFromUser(user);
        model.addAttribute("userDto", userDto);
        return "users";
    }

    @GetMapping("/{page}") //TODO delete
    List<UUID> getPage(@PathVariable("page") UUID page, Model model) {
        return asList(UUID.randomUUID(), UUID.randomUUID());
    }

    @PreAuthorize("hasAuthority('users:read')") //TODO search?
    @GetMapping("/all")
    List<UUID> getPages() {
        return asList(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
    }

    @PreAuthorize("hasAuthority('users:read')")
    @GetMapping("/suc")
    String getPageSuccess() {
        return "success";
    }

    @GetMapping("/auth/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/auth/register")
    public String getRegistrationPage(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "register";
    }

    @GetMapping("/auth/success")
    public String getSuccessPage() {
        return "success";
    }

    @PostMapping("/auth/confirm")
    @SneakyThrows
    public String registerNewUser(@ModelAttribute @Valid UserDto userDto, Model model,
                                        BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                        HttpServletRequest request) {

        model.addAttribute("userDto", userDto);
        User user = userFromDto(userDto);
        userService.saveUser(user);
        request.login(userDto.getLogin(), userDto.getPassword());
        redirectAttributes.addAttribute("login", user.getLogin());
        return "redirect:/users/{login}"; //TODO forward vs redirect
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

    private UserDto dtoFromUser(User user) {
        return UserDto.builder()
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
