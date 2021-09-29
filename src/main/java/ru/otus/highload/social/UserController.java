package ru.otus.highload.social;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
import static ru.otus.highload.social.UserController.REST_URL;

@RestController
@RequestMapping(REST_URL)
public class UserController {

    static final String REST_URL = "/accounts";

    @GetMapping(REST_URL + "/{page}")
    List<UUID> getPage(@PathVariable("page") UUID page) {
        return asList(UUID.randomUUID(), UUID.randomUUID());
    }

}
