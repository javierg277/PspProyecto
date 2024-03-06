package org.example.model.domain;

import java.util.Objects;

public class User {
    private int id;
    private String username;
    private String password;
    private double money;

    public User() {
        this.id = -1;
        this.username = "";
        this.password = "";
        this.money = 0.0;
    }
    public User(String username, String password, double money) {
        this.id = -1;
        this.username = username;
        this.password = password;
        this.money = money;
    }
    public User(int id, String username, String password, double money) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.money = money;
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

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
