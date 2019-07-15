package com.epam.lab.library.domain;

import org.json.JSONObject;


public class Status {

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public Status setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Status setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Status)) return false;

        Status status = (Status) o;

        return id.equals(status.id) && name.equals(status.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    public JSONObject getAsJson() {

        JSONObject book = new JSONObject();
        book.put("id", this.id);
        book.put("name", this.name);

        return book;
    }
}
