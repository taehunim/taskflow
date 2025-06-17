package com.sparta.taskflow.domain.user.type;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum RoleType {
    ADMIN("ADMIN"),
    USER("USER");

    private final String value;

    RoleType(String value) {
        this.value = value;
    }

    public static RoleType from(String role) {
        return Arrays.stream(values())
                     .filter(roleType -> roleType.value.equals(role))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("Invalid role: " + role));
    }
}
