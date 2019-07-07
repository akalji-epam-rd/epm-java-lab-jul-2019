package com.epam.lab.library.model;

import java.sql.Date;

public class Item {

    private int id;
    private Book book;
    private User user;
    private Status status;
    private Date date;

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
}
