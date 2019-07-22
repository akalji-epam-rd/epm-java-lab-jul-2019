package com.epam.lab.library.domain;

/**
 * Role class
 */
public class Role {

    private Integer id;
    private String name;

    /**
     * Returns role id
     *
     * @return Role id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id of current role
     *
     * @param id Id number to set
     * @return Role object
     */
    public Role setId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Returns role name
     *
     * @return Name of the role
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of current role
     *
     * @param name Name of the role to set
     * @return Role object
     */
    public Role setName(String name) {
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
        if (!(o instanceof Role)) return false;

        Role role = (Role) o;

        return id.equals(role.id) && name.equals(role.name);
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
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name=" + name;
    }
}
