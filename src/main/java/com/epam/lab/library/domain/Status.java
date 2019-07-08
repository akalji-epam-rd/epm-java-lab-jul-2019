package com.epam.lab.library.domain;

import java.util.Set;

public class Status {

    private int id;
    private String name;
    private Set<Item> items;

    public int getId() {
        return id;
    }

    public Status setId(int id) {
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

    public Set<Item> getItems() {
        return items;
    }

    public Status setItems(Set<Item> items) {
        this.items = items;
        return this;
    }
}
