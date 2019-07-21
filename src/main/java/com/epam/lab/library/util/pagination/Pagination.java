package com.epam.lab.library.util.pagination;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Pagination<T> {

    private List<T> list = new ArrayList<T>();
    private int total;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public JSONObject getAsJson() {

        JSONObject pagination = new JSONObject();
        pagination.put("total", this.total);

        return pagination;
    }
}