package com.epam.lab.library.domain;

import org.json.JSONObject;

import java.sql.Date;

/**
 * Item class
 */
public class Item {

    private Integer id;
    private Book book;
    private User user;
    private Status status;
    private Date date;

    /**
     * Initializes a newly created Item object
     */
    public Item() {
    }

    /**
     * Initializes a newly created Item object with concrete parameters
     *
     * @param id     Item id
     * @param book   What book item is instance of
     * @param user   Item user
     * @param status Item status
     */
    public Item(Integer id, Book book, User user, Status status) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.status = status;
        this.date = new Date(new java.util.Date().getTime());
    }

    /**
     * Returns item id
     *
     * @return Item id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id of current item
     *
     * @param id Id number to set
     * @return Item object
     */
    public Item setId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Returns item book
     *
     * @return Item book
     */
    public Book getBook() {
        return book;
    }

    /**
     * Sets book of current item
     *
     * @param book Book to set
     * @return Item object
     */
    public Item setBook(Book book) {
        this.book = book;
        return this;
    }

    /**
     * Returns item user
     *
     * @return Item user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets book user
     *
     * @param user Book user
     * @return Item object
     */
    public Item setUser(User user) {
        this.user = user;
        return this;
    }

    /**
     * Returns item status
     *
     * @return Item status object
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets item status
     *
     * @param status Item status
     * @return Item object
     */
    public Item setStatus(Status status) {
        this.status = status;
        return this;
    }

    /**
     * Returns item update date
     *
     * @return Item update date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets item update date
     *
     * @param date Update date
     * @return Item object
     */
    public Item setDate(Date date) {
        this.date = date;
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
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        return id.equals(item.id) && book.equals(item.book) &&
                user.equals(item.user) &&
                status.equals(item.status);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + book.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", book=" + book +
                ", user=" + user +
                ", status=" + status +
                ", date=" + date +
                '}';
    }

    /**
     * Converts item to json object
     *
     * @return json object
     */
    public JSONObject getAsJson() {
        JSONObject item = new JSONObject();
        item.put("id", this.id);
        item.put("book", this.book.getAsJson());
        item.put("user", this.user.getAsJson());
        item.put("status", this.status.getAsJson());
        item.put("date", this.date);

        return item;
    }
}
