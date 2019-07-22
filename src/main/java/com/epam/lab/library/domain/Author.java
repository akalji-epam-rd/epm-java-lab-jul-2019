package com.epam.lab.library.domain;

import java.util.Objects;
import java.util.Set;

/**
 * Book author class
 */
public class Author {

    private Integer id;
    private String name;
    private String lastName;
    private Set<Book> books;

    /**
     * Method returns author`s id
     *
     * @return Author id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Method returns author object
     *
     * @param id ID of author
     * @return Author object
     */
    public Author setId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Method returns name of author
     *
     * @return Author`s name
     */
    public String getName() {
        return name;
    }

    /**
     * Method setting author name
     *
     * @param name author`s name
     * @return Authpr object
     */
    public Author setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Method returns author last name
     *
     * @return Author`s last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Method setting author last name
     *
     * @param lastName Author`s last name
     * @return Author object
     */
    public Author setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Method returns collection of books
     *
     * @return Set of books
     */
    public Set<Book> getBooks() {
        return books;
    }

    /**
     * Method setting bookset for current author
     *
     * @param books Set of books to set
     * @return Author object
     */
    public Author setBooks(Set<Book> books) {
        this.books = books;
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
        Author author = (Author) o;
        return Objects.equals(id, author.id) &&
                Objects.equals(name, author.name) &&
                Objects.equals(lastName, author.lastName);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", books=" + books +
                '}';
    }
}
