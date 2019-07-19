package com.epam.lab.library.util.filter;

import com.epam.lab.library.domain.Book;
import com.epam.lab.library.domain.Status;
import com.epam.lab.library.domain.User;

public class ItemFilter {

    private Status status;
    private Book book;
    private User user;

    public Status getStatus() {
        return status;
    }

    public ItemFilter setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Book getBook() {
        return book;
    }

    public ItemFilter setBook(Book book) {
        this.book = book;
        return this;
    }

    public User getUser() {
        return user;
    }

    public ItemFilter setUser(User user) {
        this.user = user;
        return this;
    }
}