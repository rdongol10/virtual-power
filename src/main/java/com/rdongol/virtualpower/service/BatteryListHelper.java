package com.rdongol.virtualpower.service;

import com.rdongol.virtualpower.model.entity.Battery;
import com.rdongol.virtualpower.model.request.PostCodeRequest;
import com.rdongol.virtualpower.model.response.BatteriesResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatteryListHelper {

    private final BatteryService batteryService;

    public BatteryListHelper(BatteryService batteryService) {
        this.batteryService = batteryService;
    }

    private BatteriesResponse getEmptyBatteryResponse() {
        return new BatteriesResponse(new ArrayList<>(), 0, 0.0);
    }

    public BatteriesResponse getBatteries(PostCodeRequest postCodeRequest) {

        if (postCodeRequest == null) {
            return getEmptyBatteryResponse();
        }

        if (postCodeRequest.getPostCodes() == null || postCodeRequest.getPostCodes().isEmpty()) {
            return getEmptyBatteryResponse();
        }

        List<Battery> batteries = this.batteryService.findByPostcodes(postCodeRequest.getPostCodes());
        if (batteries.isEmpty()) {
            return getEmptyBatteryResponse();
        }

        List<String> batteryNames = batteries.stream().map(Battery::getName).sorted().collect(Collectors.toList());
        long totalWatts = batteries.stream().mapToInt(Battery::getWattCapacity).sum();
        double averageWatts = ((double) totalWatts) / batteries.size();


        return new BatteriesResponse(batteryNames, totalWatts, averageWatts);
    }

}
