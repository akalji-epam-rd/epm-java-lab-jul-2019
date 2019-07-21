package com.epam.lab.library.mock;

import com.epam.lab.library.domain.Book;
import com.epam.lab.library.domain.Item;
import com.epam.lab.library.domain.Status;
import com.epam.lab.library.domain.User;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ItemMock {

    private static List<Item> mockItems = new ArrayList<>();

    public static List<Item> getItemMockList() {

        List<Book> books = BookMock.getBookMockList();
        List<User> users = UserMock.getUserMockList();
        List<Status> statuses = StatusMock.getStatusMockList();

        // create items without user and status 'available'
        Integer id = 1;
        for (int i = 0; i < 5; i++) {
            for (int j = 1; j <= 3; j++) {
                mockItems.add(new Item(id++, books.get(i), null, statuses.get(0)));
            }
        }

        // create items with users and different status (except available status)
        mockItems.add(new Item(id++, books.get(6), users.get(0), statuses.get(2)));
        mockItems.add(new Item(id++, books.get(6), users.get(1), statuses.get(3)));
        mockItems.add(new Item(id++, books.get(6), users.get(2), statuses.get(3)));
        mockItems.add(new Item(id++, books.get(5), users.get(3), statuses.get(1)));
        mockItems.add(new Item(id++, books.get(5), users.get(0), statuses.get(2)));
        mockItems.add(new Item(id++, books.get(5), users.get(1), statuses.get(2)));
        mockItems.add(new Item(id++, books.get(7), users.get(2), statuses.get(1)));
        mockItems.add(new Item(id++, books.get(7), users.get(3), statuses.get(1)));
        mockItems.add(new Item(id++, books.get(7), users.get(0), statuses.get(1)));
        mockItems.add(new Item(id++, books.get(7), users.get(2), statuses.get(3)));
        mockItems.add(new Item(id++, books.get(7), users.get(2), statuses.get(3)));
        mockItems.add(new Item(id++, books.get(1), users.get(2), statuses.get(2)));


        return mockItems;

    }

}
