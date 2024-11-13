package com.fmi.master.solarparks.dto.create;

public class CreateSiteDTO {
    private String name;
    private String address;
    private Double configCost;
    private Double otherCost;
    private int active;
    private long project;

    public String getName() {
        return name;
    }

    public CreateSiteDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public CreateSiteDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public Double getConfigCost() {
        return configCost;
    }

    public CreateSiteDTO setConfigCost(Double configCost) {
        this.configCost = configCost;
        return this;
    }

    public Double getOtherCost() {
        return otherCost;
    }

    public CreateSiteDTO setOtherCost(Double otherCost) {
        this.otherCost = otherCost;
        return this;
    }

    public int getActive() {
        return active;
    }

    public CreateSiteDTO setActive(int active) {
        this.active = active;
        return this;
    }

    public long getProject() {
        return project;
    }

    public CreateSiteDTO setProject(long project) {
        this.project = project;
        return this;
    }
}
