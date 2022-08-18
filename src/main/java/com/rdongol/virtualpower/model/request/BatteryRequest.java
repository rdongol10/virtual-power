package com.rdongol.virtualpower.model.request;

import java.util.Objects;

public class BatteryRequest implements Request {
    private Long serialNumber;
    private String name;
    private String postCode;
    private int wattCapacity;

    public Long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public int getWattCapacity() {
        return wattCapacity;
    }

    public void setWattCapacity(int wattCapacity) {
        this.wattCapacity = wattCapacity;
    }

    @Override
    public int hashCode() {

        return (name == null ? 0 : name.hashCode());

    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (!(obj instanceof BatteryRequest)) return false;
        BatteryRequest that = (BatteryRequest) obj;
        return Objects.equals(name, that.name);
    }
}
