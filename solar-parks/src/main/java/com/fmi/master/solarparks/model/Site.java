package com.fmi.master.solarparks.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sites")
@AllArgsConstructor
@NoArgsConstructor
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private Double configCost;
    private Double otherCost;
    @Column(name = "is_active")
    private int active = 1;

    @ManyToOne(fetch = FetchType.EAGER)
    private Project project;

    public Long getId() {
        return id;
    }

    public int isActive() {
        return active;
    }

    public Site setActive(int active) {
        this.active = active;
        return this;
    }

    public Site setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Site setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Site setAddress(String address) {
        this.address = address;
        return this;
    }

    public Double getConfigCost() {
        return configCost;
    }

    public Site setConfigCost(Double configCost) {
        this.configCost = configCost;
        return this;
    }

    public Double getOtherCost() {
        return otherCost;
    }

    public Site setOtherCost(Double otherCost) {
        this.otherCost = otherCost;
        return this;
    }

    public Project getProject() {
        return project;
    }

    public Site setProject(Project project) {
        this.project = project;
        return this;
    }
}
