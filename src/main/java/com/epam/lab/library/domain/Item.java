package com.epam.lab.library.domain;


import java.util.Date;
import java.util.Objects;

public class Item {

    private Integer id;
    private Book book;
    private User user;
    private Status status;
    private Date date;

    public Item() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return Objects.equals(id, item.id) &&
                book.equals(item.book) &&
                user.equals(item.user) &&
                status.equals(item.status);
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
}
