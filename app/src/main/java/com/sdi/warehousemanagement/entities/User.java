package com.sdi.warehousemanagement.entities;

/**
 * Created by root on 22.01.2018.
 */

public class User {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private double lastName;

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public double getLastName() {
        return lastName;
    }

    public void setLastName(double lastName) {
        this.lastName = lastName;
    }

}
