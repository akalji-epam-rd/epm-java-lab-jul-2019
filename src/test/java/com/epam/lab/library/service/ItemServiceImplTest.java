package com.epam.lab.library.service;

import com.epam.lab.library.dao.interfaces.ItemDao;
import com.epam.lab.library.domain.Book;
import com.epam.lab.library.domain.Item;
import com.epam.lab.library.domain.Status;
import com.epam.lab.library.domain.User;
import com.epam.lab.library.mock.ItemMock;
import com.epam.lab.library.service.interfaces.ItemService;
import com.epam.lab.library.util.filter.ItemFilter;
import com.epam.lab.library.util.pagination.Paging;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.Assert.assertEquals;

public class ItemServiceImplTest {

    private static List<Item> itemMockList;
    private static ItemDao itemDaoMock;

    // all available items
    private static ItemFilter filterForAllAvailableItems = new ItemFilter()
            .setStatus(new Status(1));

    // all ordered items by user
    private static ItemFilter filterOrderedItemsByUser = new ItemFilter()
            .setUser(new User(2))
            .setStatus(new Status(4));

    // all items on hands
    private static  ItemFilter filterAllItemsOnHands = new ItemFilter()
            .setStatus(new Status(2));

    // all available items for book
    private static ItemFilter filterAvailableItemsByBook = new ItemFilter()
            .setBook(new Book(1))
            .setStatus(new Status(1));

    // all items for book
    private static ItemFilter filterItemsByBook = new ItemFilter()
            .setBook(new Book(1));

    @BeforeClass
    public static void initialize() throws SQLException {

        itemMockList = ItemMock.getItemMockList();
        itemDaoMock = Mockito.mock(ItemDao.class);

        Mockito.when(itemDaoMock.getAll(null, null)).thenReturn(itemMockList);
        Mockito.when(itemDaoMock.getAll(filterForAllAvailableItems, null))
                .thenReturn(itemMockList.stream()
                        .filter(item -> item.getStatus().getName().equals("available"))
                        .collect(Collectors.toList())
                );
        Mockito.when(itemDaoMock.getAll(filterOrderedItemsByUser, null))
                .thenReturn(itemMockList.stream()
                        .filter(item ->
                                item.getUser() != null &&
                                        item.getUser().getId() == 2 &&
                                        item.getStatus().getName().equals("ordered")
                        ).collect(Collectors.toList())
                );
        Mockito.when(itemDaoMock.getAll(filterAllItemsOnHands, null))
                .thenReturn(itemMockList.stream()
                        .filter(item -> item.getStatus().getName().equals("on hands"))
                        .collect(Collectors.toList())
                );
        Mockito.when(itemDaoMock.getAll(filterAvailableItemsByBook, null))
                .thenReturn(itemMockList.stream()
                        .filter(item ->
                                item.getStatus().getName().equals("available") &&
                                        item.getBook().getId() == 1
                        ).collect(Collectors.toList())
                );
        Mockito.when(itemDaoMock.getAll(filterItemsByBook, null))
                .thenReturn(itemMockList.stream()
                        .filter(item -> item.getBook().getId() == 1)
                        .collect(Collectors.toList())
                );
        Mockito.when(itemDaoMock.getAll(null, new Paging()))
                .thenReturn(itemMockList.stream()
                        .limit(10).collect(Collectors.toList())
                );
        Mockito.when(itemDaoMock.getAll(null, new Paging().setPageNumber(3).setCountPerPage(5)))
                .thenReturn(itemMockList.stream()
                        .skip(10).limit(5).collect(Collectors.toList())
                );
    }

    @Test
    public void getAllWithoutPaginationAndFilter() throws Exception {
        ItemService service = new ItemServiceImpl(itemDaoMock);
        assertEquals(itemMockList, service.getAll(null, null));
    }

    @Test
    public void getAllAvailableItems() throws Exception {
        ItemService service = new ItemServiceImpl(itemDaoMock);
        List<Item> allAvailableItems = itemMockList.stream()
                .filter(item -> item.getStatus().getName().equals("available"))
                .collect(Collectors.toList());
        assertEquals(allAvailableItems, service.getAll(filterForAllAvailableItems, null));
    }

    @Test
    public void getAllOrderedItemsByUser() throws Exception {
        ItemService service = new ItemServiceImpl(itemDaoMock);
        List<Item> allOrderedItemsByUser = itemMockList.stream()
                .filter(item ->
                        item.getUser() != null &&
                                item.getUser().getId() == 2 &&
                                item.getStatus().getName().equals("ordered")
                ).collect(Collectors.toList());
        assertEquals(allOrderedItemsByUser, service.getAll(filterOrderedItemsByUser, null));
    }

    @Test
    public void getAllItemsOnHand() throws Exception {
        ItemService service = new ItemServiceImpl(itemDaoMock);
        List<Item> allItemsOnHand = itemMockList.stream()
                .filter(item -> item.getStatus().getName().equals("on hands"))
                .collect(Collectors.toList());
        assertEquals(allItemsOnHand, service.getAll(filterAllItemsOnHands, null));
    }

    @Test
    public void getAllAvailableItemsByBook() throws Exception {
        ItemService service = new ItemServiceImpl(itemDaoMock);
        List<Item> allAvailableItemsByBook = itemMockList.stream()
                .filter(item ->
                        item.getStatus().getName().equals("available") &&
                                item.getBook().getId() == 1
                ).collect(Collectors.toList());
        assertEquals(allAvailableItemsByBook, service.getAll(filterAvailableItemsByBook, null));
    }

    @Test
    public void getAllItemsByBook() throws Exception {
        ItemService service = new ItemServiceImpl(itemDaoMock);
        List<Item> allItemsByBook = itemMockList.stream()
                .filter(item -> item.getBook().getId() == 1)
                .collect(Collectors.toList());
        assertEquals(allItemsByBook, service.getAll(filterItemsByBook, null));
    }

    @Test
    public void getAllFirstTenRows() throws Exception {
        ItemService service = new ItemServiceImpl(itemDaoMock);
        List<Item> allFirstTenRows = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            allFirstTenRows.add(itemMockList.get(i));
        }
        assertEquals(allFirstTenRows, service.getAll(null, new Paging()));
    }

    @Test
    public void getAllFiveRowsFromThirdPage() throws Exception {
        ItemService service = new ItemServiceImpl(itemDaoMock);
        List<Item> allSevenToNineteenRows = new ArrayList<>();
        for (int i = 10; i < 15; i++) {
            allSevenToNineteenRows.add(itemMockList.get(i));
        }
        assertEquals(allSevenToNineteenRows, service.getAll(null, new Paging().setPageNumber(3).setCountPerPage(5)));
    }
}