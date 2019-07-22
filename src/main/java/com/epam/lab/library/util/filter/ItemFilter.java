package com.epam.lab.library.util.filter;

import com.epam.lab.library.domain.Book;
import com.epam.lab.library.domain.Status;
import com.epam.lab.library.domain.User;

/**
 * Class for filtering items
 */
public class ItemFilter {

    private Status status;
    private Book book;
    private User user;

    /**
     * Returns status object of current filer
     *
     * @return Status object
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Setting status for filter object
     *
     * @param status Status object
     * @return Item filter
     */
    public ItemFilter setStatus(Status status) {
        this.status = status;
        return this;
    }

    /**
     * Returns book object of current filer
     *
     * @return Book object
     */
    public Book getBook() {
        return book;
    }

    /**
     * Setting book for filter object
     *
     * @param book Book object
     * @return Item filter
     */
    public ItemFilter setBook(Book book) {
        this.book = book;
        return this;
    }

    /**
     * Returns user object of current filer
     *
     * @return User object
     */
    public User getUser() {
        return user;
    }

    /**
     * Setting user for filter object
     *
     * @param user User object
     * @return Item filter
     */
    public ItemFilter setUser(User user) {
        this.user = user;
        return this;
    }
}