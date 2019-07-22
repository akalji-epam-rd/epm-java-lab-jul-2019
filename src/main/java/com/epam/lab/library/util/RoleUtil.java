package com.epam.lab.library.util;

import com.epam.lab.library.domain.Role;

import java.util.Set;

/**
 * Role util class
 */
public class RoleUtil {

    /**
     * Returns if role of user exists in role set
     *
     * @param role  Role of user
     * @param roles Existing role set
     * @return {@code true} if this role exist in role set; {@code false} otherwise.
     */
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
