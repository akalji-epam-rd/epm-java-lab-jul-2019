package com.epam.lab.library.domain;

import org.json.JSONObject;

import java.util.Objects;
import java.util.Set;

/**
 * User class
 */
public class User {

    private Integer id;
    private String email;
    private String password;
    private String name;
    private String lastName;
    private Set<Role> roles;

    /**
     * Initializes a newly created User object
     */
    public User() {
    }

    /**
     * Initializes a newly created User object with certain id
     *
     * @param id User id
     */
    public User(int id) {
        this.id = id;
    }

    /**
     * Returns user id
     *
     * @return User id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id of current user
     *
     * @param id Id number to set
     * @return User object
     */
    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Returns user email
     *
     * @return Email of user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets user email
     *
     * @param email Email
     * @return User object
     */
    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Returns user password
     *
     * @return User password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets user password
     *
     * @param password User password
     * @return User object
     */
    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Returns username
     *
     * @return User name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets user name
     *
     * @param name User name
     * @return User object
     */
    public User setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Returns user last name
     *
     * @return User last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets user name
     *
     * @param lastName User last name
     * @return User object
     */
    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Returns set of user roles
     *
     * @return User role set
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Sets user roles
     *
     * @param roles User roles
     * @return User object
     */
    public User setRoles(Set<Role> roles) {
        this.roles = roles;
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
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        if (!email.equals(user.email)) return false;
        if (!password.equals(user.password)) return false;
        if (!Objects.equals(name, user.name)) return false;
        if (!Objects.equals(lastName, user.lastName)) return false;
        return Objects.equals(roles, user.roles);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        return result;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roles=" + roles +
                '}';
    }

    /**
     * convert item to json object
     *
     * @return json object
     */
    public JSONObject getAsJson() {
        JSONObject user = new JSONObject();

        user.put("id", this.id);
        user.put("email", this.email);
        user.put("name", this.name);
        user.put("lastName", this.lastName);
        user.put("roles", this.roles); // TODO: create method getAsJson for Role class

        return user;
    }
}
