package com.epam.lab.library;


import com.epam.lab.library.domain.Author;
import com.epam.lab.library.domain.Book;
import com.epam.lab.library.service.AuthorService;
import com.epam.lab.library.service.BookService;

import java.util.List;

public class Application {

    public static void main(String[] args) {
        AuthorService service = new AuthorService();
        List<Author> authors = service.findByLastName("ozd");
    }
}
