package com.fmi.master.solarparks.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "projects")
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double cost;
    @Column(name = "is_active")
    private int active = 1;

    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;
    @ManyToMany
    @JoinTable(
            name = "contacts_projects",
            joinColumns = @JoinColumn(name = "projects_id"),
            inverseJoinColumns = @JoinColumn(name = "contacts_id")
    )
    private List<Contact> contacts;

    public int isActive() {
        return active;
    }

    public Project setActive(int active) {
        this.active = active;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Project setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Project setName(String name) {
        this.name = name;
        return this;
    }

    public double getCost() {
        return cost;
    }

    public Project setCost(double cost) {
        this.cost = cost;
        return this;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Project setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public Project setContacts(List<Contact> contacts) {
        this.contacts = contacts;
        return this;
    }
}
