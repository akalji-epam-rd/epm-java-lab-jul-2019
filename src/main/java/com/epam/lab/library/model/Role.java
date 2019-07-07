package com.epam.lab.library.model;

import java.util.Set;

public class Role {

    private int id;
    private String name;
    private Set<User> users;

    public String getName() {
        return name;
    }

    public Role setName(String name) {
        this.name = name;
        return this;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Role setUsers(Set<User> users) {
        this.users = users;
        return this;
    }
}
