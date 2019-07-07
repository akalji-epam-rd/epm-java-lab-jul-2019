package com.epam.lab.library.model;

import java.util.Set;

public class Status {

    private int id;
    private String name;
    private Set<Item> items;

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
