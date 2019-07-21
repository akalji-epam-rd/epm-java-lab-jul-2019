package com.epam.lab.library.mock;

import com.epam.lab.library.domain.Book;

import java.util.ArrayList;
import java.util.List;

public class BookMock {

    private static List<Book> mockBooks = new ArrayList<>();

    public static List<Book> getBookMockList() {

        List<String> names = new ArrayList<>() ;
        names.add("Anna Karenina");
        names.add("Love and Death");
        names.add("Dogs");
        names.add("Cats");
        names.add("Animals");
        names.add("Mems");
        names.add("Politics");
        names.add("Psychology");

        List<String> descriptions = new ArrayList<>();
        descriptions.add("about Anna Karenina");
        descriptions.add("about Love and Death");
        descriptions.add("about dogs");
        descriptions.add("about cats");
        descriptions.add("about animals");
        descriptions.add("about mems");
        descriptions.add("about politics");
        descriptions.add("about psychology");

        for (int i = 1; i <= 8; i++) {
            mockBooks.add(getBook(i, names.get(i-1), descriptions.get(i-1)));
        }

        return mockBooks;

    }
    
    private static Book getBook(Integer id, String name, String description) {
        Book book = new Book(id);
        book.setName(name);
        book.setDescription(description);
        
        return book;
    }

}
