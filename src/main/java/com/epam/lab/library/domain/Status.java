package com.epam.lab.library.domain;

import java.util.Set;

public class Status {

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public Status setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Status setName(String name) {
        this.name = name;
        return this;
    }
}
