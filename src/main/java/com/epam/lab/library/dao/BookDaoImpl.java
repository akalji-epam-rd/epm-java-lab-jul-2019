package com.epam.lab.library.dao;

import com.epam.lab.library.dao.interfaces.BookDao;
import com.epam.lab.library.connectionpool.ConnectionPool;
import com.epam.lab.library.domain.Author;
import com.epam.lab.library.domain.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookDaoImpl implements BookDao {

    private ConnectionPool pool = ConnectionPool.getInstance();


    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    public Book getById(int id) {

        Connection connection = null;
        try {
            connection = pool.getConnection();

            String query = "SELECT books.id as id, books.name as bname, description, authors.id as aid, authors.name as aname, authors.lastname as alastname " +
                    "FROM books " +
                    "LEFT JOIN authors_books ON books.id = authors_books.book_id " +
                    "LEFT JOIN authors ON authors_books.author_id = authors.id " +
                    "WHERE books.id=" + id;

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            Book book = new Book();
            book.setId(resultSet.getInt("id"))
                    .setName(resultSet.getString("bname"))
                    .setDescription(resultSet.getString("description"));
            Set<Author> authors = new HashSet<>();
            do {
                Author author = new Author();
                author.setId(resultSet.getInt("aid"))
                        .setName(resultSet.getString("aname"))
                        .setLastName(resultSet.getString("alastname"));
                authors.add(author);
            } while (resultSet.next());
            book.setAuthors(authors);
            return book;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public List<Book> getAllByName(String name) {
        return null;
    }

    @Override
    public Integer save(Book book) {
        Connection connection = null;

        try {
            connection = pool.getConnection();

            String query = "INSERT INTO books VALUES(" + book.getName() + ", " + book.getDescription() + ")";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Book book) {

    }

    @Override
    public boolean delete(Book book) {
        return false;
    }

}
