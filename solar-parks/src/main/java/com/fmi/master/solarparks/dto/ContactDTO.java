package com.fmi.master.solarparks.dto;

import java.util.ArrayList;
import java.util.List;

public class ContactDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private boolean isActive;
    private List<ProjectDTO> projects = new ArrayList<>();

    public ContactDTO() {
    }

    public long getId() {
        return id;
    }

    public ContactDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public ContactDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ContactDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public ContactDTO setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ContactDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public ContactDTO setActive(boolean active) {
        isActive = active;
        return this;
    }

    public List<ProjectDTO> getProjects() {
        return projects;
    }

    public ContactDTO setProjects(List<ProjectDTO> projects) {
        this.projects = projects;
        return this;
    }
}
