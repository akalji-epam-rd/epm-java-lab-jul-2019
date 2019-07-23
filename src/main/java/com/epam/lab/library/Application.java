package com.epam.lab.library;


import com.epam.lab.library.domain.Book;
import com.epam.lab.library.service.BookService;

import java.util.List;

public class Application {

    public static void main(String[] args) {
        BookService service = new BookService();
        List<Book> books = service.findByNameAndAuthorLastName("Anna", null);
    }
}
