package com.epam.lab.library.util.pagination;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for pagination
 */
public class Pagination<T> {

    private List<T> list = new ArrayList<T>();
    private int total;

    /**
     * Returns list of objects
     *
     * @return List of objects
     */
    public List<T> getList() {
        return list;
    }

    /**
     * Sets list of object
     *
     * @param list List of object
     */
    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * Returns total number of objects
     *
     * @return Amount of objects
     */
    public int getTotal() {
        return total;
    }

    /**
     * Sets total objects number
     *
     * @param total Amount of objects
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * convert item to json object
     *
     * @return json object
     */
    public JSONObject getAsJson() {

        JSONObject pagination = new JSONObject();
        pagination.put("total", this.total);
        return pagination;
    }
}