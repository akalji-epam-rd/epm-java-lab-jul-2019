package com.epam.lab.library;

import com.epam.lab.library.dao.BookDaoImpl;
import com.epam.lab.library.domain.Author;
import com.epam.lab.library.domain.Book;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Set;

public class Application {

    public static void main(String[] args) {
        BookDaoImpl dao = new BookDaoImpl();
        Book book = dao.getById(2);
        System.out.println(book.getName());
        System.out.println(book.getDescription());
        Set<Author> authors = book.getAuthors();
        for (Author a : authors) {
            System.out.println(a.getName());
            System.out.println(a.getLastName());
        }
    }
}