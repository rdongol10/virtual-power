package com.rdongol.virtualpower.service;

import com.rdongol.virtualpower.commons.utils.Utils;
import com.rdongol.virtualpower.exception_handler.exception.UnProcessableEntityException;
import com.rdongol.virtualpower.model.entity.Battery;
import com.rdongol.virtualpower.model.request.BatteryRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatterSaveHelper {

    private List<String> errors;
    private final BatteryService batteryService;

    public BatterSaveHelper(BatteryService batteryService) {
        this.batteryService = batteryService;
    }

    public List<Battery> processBatteries(List<BatteryRequest> batteryRequests) {

        batteryRequests = this.removeDuplicate(batteryRequests);
        this.validateBatteryRequests(batteryRequests);

        List<Battery> batteries = batteryRequests.stream().map(
                        fromValue -> Utils.mapObject(fromValue, Battery.class))
                .collect(Collectors.toList());

        if (this.errors.isEmpty()) {
            return this.batteryService.saveAll(batteries);
        } else {
            throw new UnProcessableEntityException(this.errors, "Un processable entity");
        }
    }

    private List<BatteryRequest> removeDuplicate(List<BatteryRequest> batteryRequests) {
        return new ArrayList<>(new HashSet<>(batteryRequests));
    }

    private void validateBatteryRequests(List<BatteryRequest> batteryRequests) {
        errors = new ArrayList<>();
        int count = 1;
        for (BatteryRequest batteryRequest : batteryRequests) {
            long sequenceNumber = batteryRequest.getSerialNumber() != null ? batteryRequest.getSerialNumber() : count;
            if (StringUtils.isEmpty(batteryRequest.getName())) {
                errors.add("Name is missing for entity for " + sequenceNumber + " element ");
            }

            if (StringUtils.isEmpty(batteryRequest.getPostCode())) {
                errors.add("Post Code is missing for " + sequenceNumber + " element ");
            }

            if (batteryRequest.getWattCapacity() <= 0) {
                errors.add("Watt capacity is less than 0 for  " + sequenceNumber + " element ");
            }

            if (StringUtils.isNotEmpty(batteryRequest.getPostCode()) &&
                    StringUtils.isNotEmpty(batteryRequest.getName()) &&
                    batteryService.existsByName(batteryRequest.getName())
            ) {
                errors.add("Name:" + batteryRequest.getName() + " already exists");
            }
        }
    }


}
