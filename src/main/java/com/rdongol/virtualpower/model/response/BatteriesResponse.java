package com.rdongol.virtualpower.model.response;

import java.util.List;

public class BatteriesResponse implements Response {

    private List<String> batteries;
    private long totalWatts;
    private double averageWatt;

    public BatteriesResponse(List<String> batteries, long totalWatts, double averageWatt) {
        this.batteries = batteries;
        this.totalWatts = totalWatts;
        this.averageWatt = averageWatt;
    }

    public List<String> getBatteries() {
        return batteries;
    }

    public void setBatteries(List<String> batteries) {
        this.batteries = batteries;
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
