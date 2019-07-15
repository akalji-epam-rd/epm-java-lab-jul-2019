package com.epam.lab.library.domain;

import org.json.JSONObject;

import java.sql.Date;

public class Item {

    private Integer id;
    private Book book;
    private User user;
    private Status status;
    private Date date;

    public Integer getId() {
        return id;
    }

    public Item setId(Integer id) {
        this.id = id;
        return this;
    }

    public Book getBook() {
        return book;
    }

    public Item setBook(Book book) {
        this.book = book;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Item setUser(User user) {
        this.user = user;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public Item setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Item setDate(Date date) {
        this.date = date;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        return id.equals(item.id) && book.equals(item.book) &&
                user.equals(item.user) &&
                status.equals(item.status);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + book.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

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
