package com.fmi.master.solarparks.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int numberOfProjects;
    @Column(name = "is_active")
    private int active = 1;

    public int isActive() {
        return active;
    }

    public Customer setActive(int active) {
        this.active = active;
        return this;
    }

    public int getNumberOfProjects() {
        return numberOfProjects;
    }

    public Customer setNumberOfProjects(int numberOfProjects) {
        this.numberOfProjects = numberOfProjects;
        return this;
    }

    public String getName() {
        return name;
    }

    public Customer setName(String name) {
        this.name = name;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Customer setId(Long id) {
        this.id = id;
        return this;
    }
}
