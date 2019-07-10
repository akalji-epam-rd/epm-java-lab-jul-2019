package com.epam.lab.library.domain;

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
}
