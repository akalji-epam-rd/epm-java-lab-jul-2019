package com.epam.lab.library.domain;

import java.util.Set;

public class Role {

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public Role setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Role setName(String name) {
        this.name = name;
        return this;
    }
}
