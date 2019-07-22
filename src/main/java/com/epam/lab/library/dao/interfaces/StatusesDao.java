package com.epam.lab.library.dao.interfaces;

import com.epam.lab.library.domain.Status;

import java.util.List;

/**
 * Item statuses data access object
 */
public interface StatusesDao {

    List<Status> getAll();

    Status getById(Integer id);
}
