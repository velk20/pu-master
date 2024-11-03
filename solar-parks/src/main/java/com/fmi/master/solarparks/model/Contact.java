package com.fmi.master.solarparks.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "contacts")
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    @ManyToMany(mappedBy = "contacts")
    private List<Project> projects;

    public Long getId() {
        return id;
    }

    public Contact setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Contact setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Contact setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Contact setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Contact setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public Contact setProjects(List<Project> projects) {
        this.projects = projects;
        return this;
    }


}

