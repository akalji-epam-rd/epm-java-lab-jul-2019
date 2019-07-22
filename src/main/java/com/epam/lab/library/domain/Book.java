package com.epam.lab.library.domain;

import org.json.JSONObject;

import java.util.Objects;
import java.util.Set;

/**
 * Book class
 */
public class Book {

    private Integer id;
    private String name;
    private String description;
    private Set<Author> authors;

    /**
     * Initializes a newly created Book object
     */
    public Book() {
    }

    /**
     * Initializes a newly created Book object with certain id
     *
     * @param id Book id
     */
    public Book(int id) {
        this.id = id;
    }

    /**
     * Returns book id
     *
     * @return Book id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id of current book
     *
     * @param id Id number to set
     * @return Book object
     */
    public Book setId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Returns book name
     *
     * @return Name of th book
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of current book
     *
     * @param name Name of the book to set
     * @return Book object
     */
    public Book setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Returns string description of book
     *
     * @return Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description of current book
     *
     * @param description Description to set
     * @return Book object
     */
    public Book setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Returns set of authors
     *
     * @return Set of authors
     */
    public Set<Author> getAuthors() {
        return authors;
    }

    public Book setAuthors(Set<Author> authors) {
        this.authors = authors;
        return this;
    }

    /**
     * Indicates whether some other object is "equal to" this one
     *
     * @param o the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) &&
                Objects.equals(name, book.name) &&
                Objects.equals(description, book.description);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", authors=" + authors +
                '}';
    }

    /**
     * Converts item to json object
     *
     * @return json object
     */
    public JSONObject getAsJson() {

        JSONObject book = new JSONObject();
        book.put("id", this.id);
        book.put("name", this.name);
        book.put("description", this.description);
        book.put("author", this.authors); // TODO: create getAsJson for Author class

        return book;
    }
}
