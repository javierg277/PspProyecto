package org.example.model.domain;

import java.util.Objects;

public class ATM {
    private int id;
    private String name;
    private boolean isInUse;

    public ATM() {
        this.id = -1;
        this.name = "";
        this.isInUse = false;
    }
    public ATM(String name) {
        this.id = -1;
        this.name = name;
        this.isInUse = false;
    }
    public ATM(int id,String name, boolean isInUse) {
        this.id = id;
        this.name = name;
        this.isInUse = isInUse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInUse() {
        return isInUse;
    }

    public void setInUse(boolean inUse) {
        isInUse = inUse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ATM atm = (ATM) o;
        return Objects.equals(name, atm.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
