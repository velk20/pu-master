package com.fmi.master.solarparks.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sites")
@AllArgsConstructor
@NoArgsConstructor
public class Sites {
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

    public Sites setActive(int active) {
        this.active = active;
        return this;
    }

    public Sites setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Sites setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Sites setAddress(String address) {
        this.address = address;
        return this;
    }

    public Double getConfigCost() {
        return configCost;
    }

    public Sites setConfigCost(Double configCost) {
        this.configCost = configCost;
        return this;
    }

    public Double getOtherCost() {
        return otherCost;
    }

    public Sites setOtherCost(Double otherCost) {
        this.otherCost = otherCost;
        return this;
    }

    public Project getProject() {
        return project;
    }

    public Sites setProject(Project project) {
        this.project = project;
        return this;
    }
}
