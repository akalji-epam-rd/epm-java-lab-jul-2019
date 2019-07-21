package com.epam.lab.library.dao;


import com.epam.lab.library.util.connectionpool.ConnectionPool;
import com.epam.lab.library.dao.interfaces.ItemDao;
import com.epam.lab.library.domain.Book;
import com.epam.lab.library.domain.Item;
import com.epam.lab.library.domain.Status;
import com.epam.lab.library.domain.User;
import com.epam.lab.library.util.filter.ItemFilter;
import com.epam.lab.library.util.pagination.Paging;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItemDaoImpl implements ItemDao {

    private ConnectionPool pool = ConnectionPool.getInstance();

    private String saveSql = "INSERT INTO library.items(book_id, user_id, status_id, date_items)" +
            " VALUES(?,?,?,?) RETURNING id";
    private String updateSql = "UPDATE library.items SET " +
            "book_id = ?, " +
            "user_id = ?, " +
            "status_id = ?, " +
            "date_items = ? " +
            "WHERE id = ? " +
            "RETURNING id";
    private String deleteSql = "DELETE FROM library.items WHERE id = ?";
    private String selectSql = "SELECT " +
            "i.id, i.date_items, " +
            "u.id AS user_id, u.email, u.password, u.name AS user_name, u.lastname, " +
            "b.id AS book_id, b.name AS book_name, b.description, " +
            "s.id AS status_id, s.name AS status_name " +
            "FROM " +
            "library.items i " +
            "LEFT JOIN library.users u ON i.user_id = u.id " +
            "LEFT JOIN library.books b ON i.book_id = b.id " +
            "LEFT JOIN library.statuses s ON i.status_id = s.id " +
            "WHERE i.id = ?";
    private String selectAllSql = "SELECT " +
            "i.id, i.date_items, " +
            "u.id AS user_id, u.email, u.password, u.name AS user_name, u.lastname, " +
            "b.id AS book_id, b.name AS book_name, b.description, " +
            "s.id AS status_id, s.name AS status_name " +
            "FROM " +
            "library.items i " +
            "LEFT JOIN library.users u ON i.user_id = u.id " +
            "LEFT JOIN library.books b ON i.book_id = b.id " +
            "LEFT JOIN library.statuses s ON i.status_id = s.id " +
            "WHERE 1=1 ";

    @Override
    public Integer save(Item item) throws SQLException {

        Integer id = null;
        Connection conn = pool.getConnection();
        conn.setAutoCommit(false);

        PreparedStatement ps = conn.prepareStatement(saveSql);
        ps.setInt(1, item.getBook().getId());
        if (item.getUser() != null) {
            ps.setInt(2, item.getUser().getId());
        } else {
            ps.setNull(2, 0);
        }
        ps.setInt(3, item.getStatus().getId());
        ps.setDate(4, new java.sql.Date(new Date().getTime()));

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                id = rs.getInt(1);
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.setAutoCommit(true);
            pool.releaseConnection(conn);
        }

        return id;
    }

    @Override
    public Integer update(Item item) throws SQLException {

        Integer id = null;
        Connection conn = pool.getConnection();
        conn.setAutoCommit(false);

        PreparedStatement ps = conn.prepareStatement(updateSql);
        ps.setInt(1, item.getBook().getId());
        if (item.getUser() != null) {
            ps.setInt(2, item.getUser().getId());
        } else {
            ps.setNull(2, 0);
        }
        ps.setInt(3, item.getStatus().getId());
        ps.setDate(4, new java.sql.Date(new Date().getTime()));
        ps.setInt(5, item.getId());

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                id = rs.getInt(1);
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.setAutoCommit(true);
            pool.releaseConnection(conn);
        }

        return id;
    }

    @Override
    public boolean delete(Item item) throws SQLException {

        Connection conn = pool.getConnection();
        conn.setAutoCommit(false);

        PreparedStatement ps = conn.prepareStatement(deleteSql);
        ps.setInt(1, item.getId());

        try {
            ps.execute();
            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.setAutoCommit(true);
            pool.releaseConnection(conn);
        }

        return false;
    }


    @Override
    public List<Item> getAll(ItemFilter filter, Paging pagination) throws SQLException {

        List<Item> itemList = new ArrayList<>();
        Connection conn = pool.getConnection();
        Statement st = conn.createStatement();

        String appendFilter = selectAllSql + appendFilter(filter);
        String paginationSql = appendFilter + appendPagination(pagination);

        try (ResultSet rs = st.executeQuery(paginationSql)) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("user_name"));
                user.setLastName(rs.getString("lastname"));
                Book book = new Book();
                book.setId(rs.getInt("book_id"));
                book.setName(rs.getString("book_name"));
                book.setDescription(rs.getString("description"));
                Status status = new Status();
                status.setId(rs.getInt("status_id"));
                status.setName(rs.getString("status_name"));
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setDate(rs.getDate("date_items"));
                item.setUser(user);
                item.setBook(book);
                item.setStatus(status);
                itemList.add(item);
            }

            return itemList;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(conn);
        }

        return null;
    }

    private String appendFilter(ItemFilter filter) {

        String filterSql = "";
        if (filter == null) {
            return filterSql;
        }

        if (filter.getStatus() != null) {
            Integer statusId = filter.getStatus().getId();
            filterSql += "AND s.id = " + statusId + " ";
        }

        if (filter.getBook() != null) {
            Integer bookId = filter.getBook().getId();
            filterSql += "AND b.id = " + bookId + " ";
        }

        if (filter.getUser() != null) {
            Integer userId = filter.getUser().getId();
            filterSql += "AND u.id = " + userId + " ";
        }

        return filterSql;
    }

    private String appendPagination(Paging paging) {

        if (paging == null) {
            return "";
        }

        int limit = paging.getCountPerPage();

        int start = paging.getPageNumber() * paging.getCountPerPage() - 10;

        // ORDER BY added to query because of using OFFSET and LIMIT make strange returning query
        return " ORDER BY i.id ASC OFFSET " + start + " LIMIT " + limit + ";";
    }

    /**
     *
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public Item getById(Integer id) throws SQLException {

        Connection conn = pool.getConnection();
        PreparedStatement ps = conn.prepareStatement(selectSql);
        ps.setInt(1, id);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("user_name"));
                user.setLastName(rs.getString("lastname"));
                Book book = new Book();
                book.setId(rs.getInt("book_id"));
                book.setName(rs.getString("book_name"));
                book.setDescription(rs.getString("description"));
                Status status = new Status();
                status.setId(rs.getInt("status_id"));
                status.setName(rs.getString("status_name"));
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setDate(rs.getDate("date_items"));
                item.setUser(user);
                item.setBook(book);
                item.setStatus(status);

                return item;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(conn);
        }

        return null;
    }

    @Override
    public Integer getTotal(ItemFilter filter) throws SQLException {

        Connection conn = pool.getConnection();
        Statement st = conn.createStatement();

        String sql = "SELECT COUNT(i.id) FROM library.items i WHERE 1=1 ";
        String filteredSql = sql + appendFilter(filter);
        int total = 0;

        try (ResultSet rs = st.executeQuery(filteredSql)) {
            if (rs.next()) {
                total = rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(conn);
        }

        return total;
    }
}
