package com.rdongol.virtualpower.service;

import com.rdongol.virtualpower.commons.utils.Utils;
import com.rdongol.virtualpower.exception_handler.exception.UnProcessableEntityException;
import com.rdongol.virtualpower.model.entity.Battery;
import com.rdongol.virtualpower.model.request.BatteryRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BatteryProcessor {

    private List<String> errors = new ArrayList<>();
    private final BatteryService batteryService;

    public BatteryProcessor(BatteryService batteryService) {
        this.batteryService = batteryService;
    }

    public List<Battery> processBatteries(List<BatteryRequest> batteryRequests) {

        batteryRequests = this.removeDuplicate(batteryRequests);
        this.validateBatteryRequests(batteryRequests);
        if (this.errors.isEmpty()) {
            return this.batteryService.saveAll(Utils.mapObjectList(Collections.singletonList(batteryRequests), Battery.class));
        } else {
            throw new UnProcessableEntityException(this.errors, "");
        }
    }


    private List<BatteryRequest> removeDuplicate(List<BatteryRequest> batteryRequests) {
        return new ArrayList<>(new HashSet<>(batteryRequests));
    }

    private void validateBatteryRequests(List<BatteryRequest> batteryRequests) {
        int count = 1;
        for (BatteryRequest batteryRequest : batteryRequests) {
            Long sequenceNumber = batteryRequest.getSerialNumber() != null ? batteryRequest.getSerialNumber() : count;
            if (StringUtils.isEmpty(batteryRequest.getName())) {
                errors.add("Name is missing for " + sequenceNumber + " element ");
            }

            if (StringUtils.isEmpty(batteryRequest.getPostcode())) {
                errors.add("Post Code is missing for " + sequenceNumber + " element ");
            }

            if (batteryRequest.getWattCapacity() <= 0) {
                errors.add("Watt capacity is less than 0 for  " + sequenceNumber + " element ");
            }

            if (StringUtils.isNotEmpty(batteryRequest.getPostcode()) &&
                    StringUtils.isNotEmpty(batteryRequest.getName()) &&
                    batteryService.existsByPostCodeAndName(batteryRequest.getPostcode(), batteryRequest.getName())
            ) {
                errors.add("PostCode:" + batteryRequest.getPostcode() + " and Name:" + batteryRequest.getName() + " already exists");
            }
        }
    }


}
