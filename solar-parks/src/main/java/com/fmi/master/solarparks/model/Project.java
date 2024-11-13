package com.fmi.master.solarparks.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", nullable = true)
    private Customer customer;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "contacts_projects")
    @JsonManagedReference
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

    public void addContact(Contact contact) {
        this.contacts.add(contact);
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

    public void removeContact(Contact contact) {
        this.contacts.remove(contact);
    }
}
