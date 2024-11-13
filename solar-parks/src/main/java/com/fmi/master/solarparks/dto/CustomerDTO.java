package com.fmi.master.solarparks.dto;

public class CustomerDTO {
    private long id;
    private String name;
    private int numberOfProjects;
    private boolean isActive;

    public long getId() {
        return id;
    }

    public CustomerDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CustomerDTO setName(String name) {
        this.name = name;
        return this;
    }

    public int getNumberOfProjects() {
        return numberOfProjects;
    }

    public CustomerDTO setNumberOfProjects(int numberOfProjects) {
        this.numberOfProjects = numberOfProjects;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public CustomerDTO setActive(boolean active) {
        isActive = active;
        return this;
    }
}
