package com.epam.lab.library.model;

import java.util.Set;


public class Book {

    private int id;
    private String name;
    private String description;
    private Set<Author> authors;
    private Set<Item> items;

    // id?

    public String getName() {
        return name;
    }

    public Book setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Book setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public Book setAuthors(Set<Author> authors) {
        this.authors = authors;
        return this;
    }

    public Set<Item> getItems() {
        return items;
    }

    public Book setItems(Set<Item> items) {
        this.items = items;
        return this;
    }
}
