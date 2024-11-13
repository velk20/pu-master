package com.fmi.master.solarparks.dto;

import java.util.ArrayList;
import java.util.List;

public class ProjectDTO {
    private long id;
    private String name;
    private double cost;
    private boolean isActive;
    private CustomerDTO customer;
    private List<ContactDTO> contacts = new ArrayList<>();

    public long getId() {
        return id;
    }

    public ProjectDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProjectDTO setName(String name) {
        this.name = name;
        return this;
    }

    public double getCost() {
        return cost;
    }

    public ProjectDTO setCost(double cost) {
        this.cost = cost;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public ProjectDTO setActive(boolean active) {
        isActive = active;
        return this;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public ProjectDTO setCustomer(CustomerDTO customer) {
        this.customer = customer;
        return this;
    }

    public List<ContactDTO> getContacts() {
        return contacts;
    }

    public ProjectDTO setContacts(List<ContactDTO> contacts) {
        this.contacts = contacts;
        return this;
    }
}
