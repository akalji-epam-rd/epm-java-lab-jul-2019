package com.epam.lab.library;

import com.epam.lab.library.dao.AuthorDaoImpl;
import com.epam.lab.library.dao.interfaces.AuthorDao;
import com.epam.lab.library.domain.Author;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

public class Application {

    public static void main(String[] args) throws MalformedURLException {
        AuthorDao authorDao = new AuthorDaoImpl();
        authorDao.save(new Author().setName("Lev").setLastName("Tolstoy"));
        authorDao.save(new Author().setName("Alex").setLastName("Pushkin"));
        authorDao.save(new Author().setName("Nikolai").setLastName("Gogol"));
    }
}