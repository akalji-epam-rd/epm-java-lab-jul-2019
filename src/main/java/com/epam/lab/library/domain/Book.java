package com.epam.lab.library.domain;

import org.json.JSONObject;

import java.util.Objects;
import java.util.Set;


public class Book {

    private Integer id;
    private String name;
    private String description;
    private Set<Author> authors;

    public Book() {
    }

    public Book(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Book setId(Integer id) {
        this.id = id;
        return this;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) &&
                Objects.equals(name, book.name) &&
                Objects.equals(description, book.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", authors=" + authors +
                '}';
    }

    public JSONObject getAsJson() {

        JSONObject book = new JSONObject();
        book.put("id", this.id);
        book.put("name", this.name);
        book.put("description", this.description);
        book.put("author", this.authors); // TODO: create getAsJson for Author class

        return book;
    }
}
