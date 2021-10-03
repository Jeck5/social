package ru.otus.highload.social.model;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static ru.otus.highload.social.model.Permission.USERS_READ;
import static ru.otus.highload.social.model.Permission.USERS_WRITE;

public enum Role {
    USER(Set.of(USERS_READ)), ADMIN(Set.of(USERS_READ, USERS_WRITE));

    @Getter
    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities(){
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

    }
}
