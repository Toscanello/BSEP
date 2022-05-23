package com.adminapp.domain.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_USER;

    @Override
    public String getAuthority() {
        return name();
    }

    public static int getIntFromRoleName(String name) {
        switch (name) {
            case "ROLE_ADMIN":
                return 0;
            case "ROLE_USER":
                return 1;
        }

        return 0;
    }
}
