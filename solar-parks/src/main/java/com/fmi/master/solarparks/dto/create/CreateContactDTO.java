package com.fmi.master.solarparks.dto.create;

import java.util.ArrayList;
import java.util.List;

public class CreateContactDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private boolean isActive;
    private List<Long> projects = new ArrayList<>();

    public String getFirstName() {
        return firstName;
    }

    public CreateContactDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public CreateContactDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public CreateContactDTO setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public CreateContactDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public CreateContactDTO setActive(boolean active) {
        isActive = active;
        return this;
    }

    public List<Long> getProjects() {
        return projects;
    }

    public CreateContactDTO setProjects(List<Long> projects) {
        this.projects = projects;
        return this;
    }
}
