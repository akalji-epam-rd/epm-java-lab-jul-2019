package com.epam.lab.library.model;

import java.util.Set;

public class User {

    private int id;
    private String email;
    private String password;
    private String name;
    private String lastName;
    private Set<Role> roles;
    private Set<Item> items;

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public User setRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Set<Item> getItems() {
        return items;
    }

    public User setItems(Set<Item> items) {
        this.items = items;
        return this;
    }
}
