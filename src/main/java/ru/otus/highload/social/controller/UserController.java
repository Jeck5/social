package ru.otus.highload.social.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;

@Controller
public class UserController {

    @GetMapping("/{page}")
    List<UUID> getPage(@PathVariable("page") UUID page) {
        return asList(UUID.randomUUID(), UUID.randomUUID());
    }

    @PreAuthorize("hasAuthority('users:read')")
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
    public String getRegistrationPage() {
        return "register";
    }

    @GetMapping("/auth/success")
    public String getSuccessPage() {
        return "success";
    }

}
