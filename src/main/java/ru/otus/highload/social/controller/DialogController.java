package ru.otus.highload.social.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.highload.social.dto.UserWithFriendsDto;
import ru.otus.highload.social.model.Dialog;
import ru.otus.highload.social.model.User;
import ru.otus.highload.social.service.DialogService;
import ru.otus.highload.social.service.UserService;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DialogController {

    private final DialogService dialogService;

    private final UserService userService;

    @GetMapping("/dialogs/{name}")
    @PreAuthorize("hasAuthority('users:read')")
    String getByName(@PathVariable("name") String name, Model model) {
        //TODO dialog with messages

        model.addAttribute("dialog", new Object());
        return "dialog";
    }

    @SneakyThrows
    @PostMapping("/dialogs")
    @PreAuthorize("hasAuthority('users:read')")
    void create(@RequestParam String name, HttpServletResponse response) {
        Dialog dialog = dialogService.createDialog(name, userService.getCurrentUserId());
        response.sendRedirect("/dialogs/" + name);
    }

}
