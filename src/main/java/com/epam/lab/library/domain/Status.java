package com.epam.lab.library.domain;

import org.json.JSONObject;

/**
 * Status object
 */
public class Status {

    private Integer id;
    private String name;

    /**
     * Initializes a newly created Status object
     */
    public Status() {
    }

    /**
     * Initializes a newly created Book object with certain id
     *
     * @param id Status id
     */
    public Status(Integer id) {
        this.id = id;
    }

    /**
     * Returns status id
     *
     * @return Status id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id of current status
     *
     * @param id Id number to set
     * @return Status object
     */
    public Status setId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Returns status name
     *
     * @return Name of the status
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of current status
     *
     * @param name Name of the status to set
     * @return Status object
     */
    public Status setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Indicates whether some other object is "equal to" this one
     *
     * @param o the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Status)) return false;

        Status status = (Status) o;

        return id.equals(status.id) && name.equals(status.name);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    /**
     * convert item to json object
     *
     * @return json object
     */
    public JSONObject getAsJson() {

        JSONObject book = new JSONObject();
        book.put("id", this.id);
        book.put("name", this.name);

        return book;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
