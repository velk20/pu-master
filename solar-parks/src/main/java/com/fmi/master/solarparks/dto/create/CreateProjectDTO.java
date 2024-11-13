package com.fmi.master.solarparks.dto.create;

import java.util.ArrayList;
import java.util.List;

public class CreateProjectDTO {
    private String name;
    private double cost;
    private int active;
    private long customer;
    private List<Long> contacts = new ArrayList<>();

    public String getName() {
        return name;
    }

    public CreateProjectDTO setName(String name) {
        this.name = name;
        return this;
    }

    public double getCost() {
        return cost;
    }

    public CreateProjectDTO setCost(double cost) {
        this.cost = cost;
        return this;
    }

    public int getActive() {
        return active;
    }

    public CreateProjectDTO setActive(int active) {
        this.active = active;
        return this;
    }

    public long getCustomer() {
        return customer;
    }

    public CreateProjectDTO setCustomer(long customer) {
        this.customer = customer;
        return this;
    }

    public List<Long> getContacts() {
        return contacts;
    }

    public CreateProjectDTO setContacts(List<Long> contacts) {
        this.contacts = contacts;
        return this;
    }
}
