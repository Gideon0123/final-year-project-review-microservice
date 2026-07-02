package com.example.REVIEW_SERVICE.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityUtils {

    public static boolean hasRole(String role) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        assert authentication != null;
        return authentication
                .getAuthorities()
                .stream()
                .anyMatch(authority ->
                        Objects.equals(authority.getAuthority(), "ROLE_" + role));
    }

}