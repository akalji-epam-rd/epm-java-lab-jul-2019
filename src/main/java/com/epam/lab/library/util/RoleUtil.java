package com.epam.lab.library.util;

import com.epam.lab.library.domain.Role;

import java.util.Set;

public class RoleUtil {

    public static boolean hasRole(String role, Set<Role> roles) {

        if (roles != null && !roles.isEmpty()) {
            for (Role r : roles) {
                if (role.equals(r.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

}
