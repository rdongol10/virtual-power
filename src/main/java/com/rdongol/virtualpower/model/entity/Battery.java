package com.rdongol.virtualpower.model.entity;

import javax.persistence.Entity;

@Entity
public class Battery extends BaseEntity {

    private String name;
    private String postcode;
    private int wattCapacity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public int getWattCapacity() {
        return wattCapacity;
    }

    public void setWattCapacity(int wattCapacity) {
        this.wattCapacity = wattCapacity;
    }
}
