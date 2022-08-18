package com.rdongol.virtualpower.model.response;

import java.util.List;

public class BatteriesResponse implements Response {

    private List<BatteryResponse> battery;
    private long totalWatts;
    private double averageWatt;

    public List<BatteryResponse> getBattery() {
        return battery;
    }

    public void setBattery(List<BatteryResponse> battery) {
        this.battery = battery;
    }

    public long getTotalWatts() {
        return totalWatts;
    }

    public void setTotalWatts(long totalWatts) {
        this.totalWatts = totalWatts;
    }

    public double getAverageWatt() {
        return averageWatt;
    }

    public void setAverageWatt(double averageWatt) {
        this.averageWatt = averageWatt;
    }
}
