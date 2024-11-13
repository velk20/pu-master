package com.fmi.master.solarparks.dto;

public class SiteDTO {
    private long id;
    private String name;
    private String address;
    private double configCost;
    private double otherCost;
    private boolean isActive;
    private ProjectDTO project;

    public long getId() {
        return id;
    }

    public SiteDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SiteDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public SiteDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public double getConfigCost() {
        return configCost;
    }

    public SiteDTO setConfigCost(double configCost) {
        this.configCost = configCost;
        return this;
    }

    public double getOtherCost() {
        return otherCost;
    }

    public SiteDTO setOtherCost(double otherCost) {
        this.otherCost = otherCost;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public SiteDTO setActive(boolean active) {
        isActive = active;
        return this;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public SiteDTO setProject(ProjectDTO project) {
        this.project = project;
        return this;
    }
}
